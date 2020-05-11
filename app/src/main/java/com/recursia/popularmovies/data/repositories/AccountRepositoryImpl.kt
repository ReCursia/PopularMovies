package com.recursia.popularmovies.data.repositories

import com.recursia.popularmovies.domain.AccountRepository
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class AccountRepositoryImpl : AccountRepository {
    override fun getUserInfo(): Single<User> {
        TODO("Not yet implemented")
    }

    override fun setUserName(name: String): Completable {
        TODO("Not yet implemented")
    }

    override fun getUserMoviesWithStatus(status: MovieStatus): Flowable<List<Movie>> {
        TODO("Not yet implemented")
    }
}