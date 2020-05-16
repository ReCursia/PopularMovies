package com.recursia.popularmovies.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.recursia.popularmovies.domain.AccountRepository
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

class AccountRepositoryImpl : AccountRepository {
    override fun getUserInfo(): Single<User> {
        // TODO implement
        return Single.just(User("test", "today"))
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
