package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.domain.models.Movie

@StateStrategyType(AddToEndSingleStrategy::class)
interface MoviesListContract : MvpView {

    // Loading
    @StateStrategyType(SkipStrategy::class)
    fun showLoading()

    @StateStrategyType(SkipStrategy::class)
    fun hideLoading()

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)

    // List
    fun setMovies(movies: List<Movie>)

    @StateStrategyType(AddToEndStrategy::class)
    fun addMovies(movies: List<Movie>)
}
