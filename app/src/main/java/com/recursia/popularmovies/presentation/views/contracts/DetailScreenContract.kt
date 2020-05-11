package com.recursia.popularmovies.presentation.views.contracts

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailScreenContract : MvpView {

    fun setMovieDetail(movie: Movie)

    fun setMovieStatus(status: MovieStatus)

    @StateStrategyType(SkipStrategy::class)
    fun shareMovie(movie: Movie)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun showMovieStatusSetMessage()
}
