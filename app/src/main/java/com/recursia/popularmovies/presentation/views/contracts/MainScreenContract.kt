package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category

@StateStrategyType(AddToEndStrategy::class)
interface MainScreenContract : MvpView {
    fun setCategoryMovies(movies: List<Movie>, category: Category)

    fun addCategoryMovies(movies: List<Movie>, category: Category)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)

}
