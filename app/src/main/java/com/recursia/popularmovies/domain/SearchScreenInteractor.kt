package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie

import io.reactivex.Single

interface SearchScreenInteractor {

    fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>>

}
