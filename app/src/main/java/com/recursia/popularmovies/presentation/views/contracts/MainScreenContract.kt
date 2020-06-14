package com.recursia.popularmovies.presentation.views.contracts

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface MainScreenContract : MvpView {
    fun setCategoryMovies(movies: List<Movie>, category: Category)

    @StateStrategyType(AddToEndStrategy::class)
    fun addCategoryMovies(movies: List<Movie>, category: Category)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)
}
