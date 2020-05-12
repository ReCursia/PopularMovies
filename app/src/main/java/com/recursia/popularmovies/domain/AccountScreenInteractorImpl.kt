package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class AccountScreenInteractorImpl(private val accountRepository: AccountRepository) : AccountScreenInteractor {
    override fun getUserInfo(): Single<User> {
        return accountRepository.getUserInfo()
    }

    override fun setUserName(name: String): Completable {
        return accountRepository.getUserInfo()
                .doOnSuccess { it.username = name }
                .flatMapCompletable { accountRepository.setUserInfo(it) }
    }

    override fun getUserMoviesWithStatus(status: MovieStatus): Flowable<List<Movie>> {
        return accountRepository.getUserMoviesWithStatus(status)
    }
}