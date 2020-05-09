package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie

import io.reactivex.Single

class MoviesListInteractorImpl(private val moviesRepository: MoviesRepository) : MoviesListInteractor {

    override fun discoverMovies(sortBy: String, page: Int, voteCount: Int, language: String): Single<List<Movie>> {
        return moviesRepository.discoverMovies(sortBy, page, voteCount, language)
    }
}
