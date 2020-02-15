package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie

import io.reactivex.Single

class SearchScreenInteractorImpl(private val moviesRepository: MoviesRepository) : SearchScreenInteractor {

    override fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>> {
        return moviesRepository.getMoviesByQuery(query, page, language)
    }
}
