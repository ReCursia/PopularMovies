package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category
import io.reactivex.Single

interface MainScreenInteractor {
    fun getMoviesWithCategory(category: Category, page: Int, language: String): Single<List<Movie>>
}
