package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie

import io.reactivex.Flowable

class FavoriteScreenInteractorImpl(private val moviesRepository: MoviesRepository) : FavoriteScreenInteractor {

    override val allFavoriteMovies: Flowable<List<Movie>>
        get() = moviesRepository.allFavoriteMovies

}
