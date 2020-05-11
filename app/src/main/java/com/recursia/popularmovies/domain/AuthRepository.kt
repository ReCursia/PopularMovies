package com.recursia.popularmovies.domain

import io.reactivex.Completable

interface AuthRepository {
    fun signIn(email: String, password: String): Completable

    fun signUp(email: String, password: String): Completable

    fun resetPassword(email: String): Completable
}