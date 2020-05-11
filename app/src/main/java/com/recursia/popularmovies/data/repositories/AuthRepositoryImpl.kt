package com.recursia.popularmovies.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.recursia.popularmovies.domain.AuthRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class AuthRepositoryImpl : AuthRepository {
    override fun signIn(email: String, password: String): Completable {
        return Completable.create {
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Completable.complete()
                        }
                        Completable.error(it.exception)
                    }
        }.subscribeOn(Schedulers.io())
    }

    override fun signUp(email: String, password: String): Completable {
        return Completable.create {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Completable.complete()
                        }
                        Completable.error(it.exception)
                    }
        }.subscribeOn(Schedulers.io())
    }

    override fun resetPassword(email: String): Completable {
        return Completable.create {
            val auth = FirebaseAuth.getInstance()
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Completable.complete()
                        }
                        Completable.error(it.exception)
                    }
        }.subscribeOn(Schedulers.io())
    }
}