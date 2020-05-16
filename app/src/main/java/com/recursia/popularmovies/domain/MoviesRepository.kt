package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category
import io.reactivex.Single

interface MoviesRepository {

    fun getMovieById(movieId: Int, language: String): Single<Movie>

    fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>>

    fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>>

    fun getMoviesWithCategory(category: Category, language: String): Single<List<Movie>>
}
