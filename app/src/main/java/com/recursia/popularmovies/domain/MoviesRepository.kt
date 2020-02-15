package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesRepository {

    val allFavoriteMovies: Flowable<List<Movie>>

    fun discoverMovies(sortBy: String, page: Int, voteCount: Int, language: String): Single<List<Movie>>

    fun getMovieById(movieId: Int, language: String): Single<Movie>

    fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>>

    fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>>

    fun makeFavoriteMovie(movie: Movie): Completable

    fun removeFavoriteMovie(movie: Movie): Completable
}
