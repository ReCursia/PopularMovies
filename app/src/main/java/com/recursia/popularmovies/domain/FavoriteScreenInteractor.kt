package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie

import io.reactivex.Flowable

interface FavoriteScreenInteractor {

    val allFavoriteMovies: Flowable<List<Movie>>
}
