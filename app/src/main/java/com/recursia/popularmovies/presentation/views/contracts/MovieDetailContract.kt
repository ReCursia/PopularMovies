package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Trailer


@StateStrategyType(AddToEndSingleStrategy::class)
interface MovieDetailContract : MvpView {
    fun setRecommendationMovies(movies: List<Movie>)

    fun setMovieDetail(movie: Movie)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun openTrailerUrl(trailer: Trailer)
}