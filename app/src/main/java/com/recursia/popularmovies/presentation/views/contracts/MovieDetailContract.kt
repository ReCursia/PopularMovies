package com.recursia.popularmovies.presentation.views.contracts

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Trailer
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MovieDetailContract : MvpView {
    fun setRecommendationMovies(movies: List<Movie>)

    @StateStrategyType(AddToEndStrategy::class)
    fun addRecommendationMovies(movies: List<Movie>)

    fun setMovieDetail(movie: Movie)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun openTrailerUrl(trailer: Trailer)
}
