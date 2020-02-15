package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Trailer

@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailScreenContract : MvpView {

    // Movie detail
    fun setMovieDetail(movie: Movie)

    fun hideMovieDetail()

    fun showMovieDetail()

    // Favorite icon
    fun setFavoriteIconOn()

    fun setFavoriteIconOff()

    fun showFavoriteIcon()

    fun hideFavoriteIcon()

    // Share
    @StateStrategyType(SkipStrategy::class)
    fun shareMovie(movie: Movie)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun showMovieAddedMessage()

    @StateStrategyType(SkipStrategy::class)
    fun showMovieRemovedMessage()

    @StateStrategyType(SkipStrategy::class)
    fun openTrailerUrl(trailer: Trailer)

    // Movie recommendations
    fun setRecommendationMovies(movies: List<Movie>)

    fun hideRecommendationMovies()

    fun showRecommendationMovies()
}
