package com.recursia.popularmovies.domain

import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class AuthScreenInteractorImpl : AuthScreenInteractor {
    override fun signIn(email: String, password: String): Completable {
        return Completable.timer(5000, TimeUnit.MILLISECONDS)
    }

    override fun signUp(email: String, password: String): Completable {
        return Completable.timer(5000, TimeUnit.MILLISECONDS)
    }

    override fun resetPassword(email: String): Completable {
        TODO("Not yet implemented")
    }
}