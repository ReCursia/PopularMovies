package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesRepository {

    fun getMoviesWithStatus(status: MovieStatus): Flowable<List<Movie>>

    fun discoverMovies(sortBy: String, page: Int, voteCount: Int, language: String): Single<List<Movie>>

    fun getMovieById(movieId: Int, language: String): Single<Movie>

    fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>>

    fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>>

    fun setMovieStatus(movie: Movie): Completable

    fun getMoviesWithCategory(category: Category, language: String): Single<List<Movie>>

}
