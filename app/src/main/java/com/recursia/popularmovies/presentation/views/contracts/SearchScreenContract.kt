package com.recursia.popularmovies.presentation.views.contracts

import com.recursia.popularmovies.domain.models.Movie
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchScreenContract : MvpView {

    fun setMovies(movies: List<Movie>)

    fun addMovies(movies: List<Movie>)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)
}
