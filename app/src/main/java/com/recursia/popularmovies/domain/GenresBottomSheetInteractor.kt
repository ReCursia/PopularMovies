package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.domain.models.Movie
import io.reactivex.Single

interface GenresBottomSheetInteractor {

    fun getGenreMovies(genre: Genre, page: Int, language: String): Single<List<Movie>>

    fun getGenres(language: String): Single<List<Genre>>
}
