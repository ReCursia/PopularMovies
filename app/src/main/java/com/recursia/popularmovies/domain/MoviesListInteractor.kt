package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie

import io.reactivex.Single

interface MoviesListInteractor {

    fun discoverMovies(sortBy: String, page: Int, voteCount: Int, language: String): Single<List<Movie>>
}
