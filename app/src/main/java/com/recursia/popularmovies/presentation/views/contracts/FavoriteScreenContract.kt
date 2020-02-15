package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.domain.models.Movie

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoriteScreenContract : MvpView {

    // List
    fun setMovies(movies: List<Movie>)

    fun showNoFavoriteScreen()

    fun hideNoFavoriteScreen()

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)
}
