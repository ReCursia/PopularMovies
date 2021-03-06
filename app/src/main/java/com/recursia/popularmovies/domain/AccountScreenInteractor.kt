package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface AccountScreenInteractor {

    fun getUserInfo(): Single<User>

    fun setUserProfileImage(imagePath: String): Completable

    fun getUserMoviesWithStatus(status: MovieStatus): Flowable<List<Movie>>
}
