package com.recursia.popularmovies.presentation.views.contracts

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface AccountScreenContract : MvpView {

    fun setMoviesWithStatus(movies: List<Movie>, status: MovieStatus)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setUserData(user: User)

    @StateStrategyType(SkipStrategy::class)
    fun showAboutDialog()

    @StateStrategyType(SkipStrategy::class)
    fun hideAboutDialog()

    @StateStrategyType(SkipStrategy::class)
    fun openGooglePlayPage()

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)
}
