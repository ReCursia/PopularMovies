package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.enums.Category

interface MainScreenInteractor {
    fun getMoviesWithCategory(category: Category, language: String)
}