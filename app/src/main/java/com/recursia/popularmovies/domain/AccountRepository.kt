package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface AccountRepository {
    fun getUserInfo(): Single<User>

    fun setUserInfo(user: User): Completable

    fun getUserMoviesWithStatus(status: MovieStatus): Flowable<List<Movie>>

    fun getMovieById(movieId: Int): Maybe<Movie>

    fun setMovieStatus(movie: Movie): Completable
}