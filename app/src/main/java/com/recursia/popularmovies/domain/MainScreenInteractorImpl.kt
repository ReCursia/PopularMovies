package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category
import io.reactivex.Single

class MainScreenInteractorImpl(
        private val moviesRepository: MoviesRepository
) : MainScreenInteractor {
    override fun getMoviesWithCategory(category: Category, language: String): Single<List<Movie>> {
        return moviesRepository.getMoviesWithCategory(category, language)
    }
}
