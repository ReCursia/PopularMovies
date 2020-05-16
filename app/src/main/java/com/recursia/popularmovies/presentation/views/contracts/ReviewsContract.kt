package com.recursia.popularmovies.presentation.views.contracts

import com.recursia.popularmovies.domain.models.Review
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ReviewsContract : MvpView {

    fun updateReview(review: Review, position: Int)

    fun setReviews(reviews: List<Review>)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)
}
