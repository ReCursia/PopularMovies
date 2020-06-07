package com.recursia.popularmovies.data.repositories

import android.net.Uri
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.recursia.popularmovies.domain.AccountRepository
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

class AccountRepositoryImpl : AccountRepository {
    override fun getUserInfo(): Maybe<User> {
        return Maybe.create<User> { emitter ->
            val user = User()
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            user.username = firebaseUser?.uid
            user.email = firebaseUser?.email

            // Image
            val storageRef = FirebaseStorage
                    .getInstance()
                    .reference
                    .child("images/${firebaseUser?.uid}")

            storageRef.downloadUrl.addOnSuccessListener {
                user.profileImagePath = it.toString()
                emitter.onSuccess(user)
            }.addOnFailureListener {
                emitter.onSuccess(user)
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun setUserProfileImage(imagePath: String): Completable {
        return Completable.create { emitter ->
            val user = FirebaseAuth.getInstance().currentUser
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/${user?.uid}")
            val uri = Uri.parse(imagePath)
            imageRef.putFile(uri)
                    .addOnSuccessListener {
                        OnSuccessListener<UploadTask.TaskSnapshot> { emitter.onComplete() }
                    }
                    .addOnFailureListener {
                        emitter.onError(it)
                    }
        }.subscribeOn(Schedulers.io())
    }

    override fun setUserInfo(user: User): Completable {
        // TODO implement
        return Completable.complete()
    }

    override fun getMovieById(movieId: Int): Maybe<Movie> {
        return Maybe.create<Movie> { emitter ->
            val user = FirebaseAuth.getInstance().currentUser
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("movies/${user?.uid}")
            ref.addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(error.toException())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val movie = findMovie(movieId, snapshot)
                            if (movie != null) {
                                emitter.onSuccess(movie.getValue(Movie::class.java) as Movie)
                            }
                            emitter.onComplete()
                        }
                    }
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun setMovieStatus(movie: Movie): Completable {
        return Completable.create { emitter ->
            val user = FirebaseAuth.getInstance().currentUser
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("movies/${user?.uid}")
            ref.addValueEventListener(
                    object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(error.toException())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val existedMovie = findMovie(movie.id, snapshot)
                            if (existedMovie == null) {
                                ref.push().setValue(movie)
                            }
                            // TODO update child if existed
                            emitter.onComplete()
                        }
                    }
            )
        }.subscribeOn(Schedulers.io())
    }

    private fun findMovie(movieId: Int, snapshot: DataSnapshot): DataSnapshot? {
        return snapshot.children
                .asSequence()
                .find { (it.getValue(Movie::class.java) as Movie).id == movieId }
    }

    private fun findMoviesWithStatus(status: MovieStatus, snapshot: DataSnapshot): List<DataSnapshot> {
        return snapshot.children
                .filter { (it.getValue(Movie::class.java) as Movie).status == status }
    }

    override fun getUserMoviesWithStatus(status: MovieStatus): Flowable<List<Movie>> {
        return Observable.create<List<Movie>> { emitter ->
            val user = FirebaseAuth.getInstance().currentUser
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("movies/${user?.uid}")
            ref.addValueEventListener(
                    object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(error.toException())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val movies = findMoviesWithStatus(status, snapshot)
                            emitter.onNext(movies.map { it.getValue(Movie::class.java) as Movie })
                        }
                    }
            )
        }.toFlowable(BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
    }
}
