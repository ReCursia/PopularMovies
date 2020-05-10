package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.MovieStatus

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
