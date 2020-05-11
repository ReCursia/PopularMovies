package com.recursia.popularmovies.domain

import io.reactivex.Completable

class AuthScreenInteractorImpl(private val authRepository: AuthRepository) : AuthScreenInteractor {
    override fun signIn(email: String, password: String): Completable {
        return authRepository.signIn(email, password)
    }

    override fun signUp(email: String, password: String): Completable {
        return authRepository.signUp(email, password)
    }

    override fun resetPassword(email: String): Completable {
        return authRepository.resetPassword(email)
    }
}