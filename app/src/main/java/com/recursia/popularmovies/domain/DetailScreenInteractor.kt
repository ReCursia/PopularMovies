package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Completable
import io.reactivex.Single

interface DetailScreenInteractor {

    fun getMovieById(movieId: Int, language: String): Single<Movie>

    fun makeFavoriteMovie(movie: Movie): Completable

    fun removeFavoriteMovie(movie: Movie): Completable

    fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>>

    fun translateReview(review: Review, lang: String): Single<Review>
}
