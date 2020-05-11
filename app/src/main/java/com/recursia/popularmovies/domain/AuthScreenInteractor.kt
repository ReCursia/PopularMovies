package com.recursia.popularmovies.domain

import io.reactivex.Completable

interface AuthScreenInteractor {

    fun signIn(email: String, password: String): Completable

    fun signUp(email: String, password: String): Completable

    fun resetPassword(email: String): Completable

}