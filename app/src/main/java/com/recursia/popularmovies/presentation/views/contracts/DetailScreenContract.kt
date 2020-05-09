package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Review
import com.recursia.popularmovies.domain.models.Trailer

@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailScreenContract : MvpView {

    // Movie detail
    fun setMovieDetail(movie: Movie)

    fun hideMovieDetail()

    fun showMovieDetail()

    // Share
    @StateStrategyType(SkipStrategy::class)
    fun shareMovie(movie: Movie)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun showMovieStatusSetMessage()

    @StateStrategyType(SkipStrategy::class)
    fun openTrailerUrl(trailer: Trailer)

    // Reviews
    fun updateReview(review: Review, position: Int)

    // Movie recommendations
    fun setRecommendationMovies(movies: List<Movie>)

    fun hideRecommendationMovies()

    fun showRecommendationMovies()
}
