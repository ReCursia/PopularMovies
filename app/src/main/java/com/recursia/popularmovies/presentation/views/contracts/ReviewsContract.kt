package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.domain.models.Review

@StateStrategyType(AddToEndSingleStrategy::class)
interface ReviewsContract : MvpView {

    fun updateReview(review: Review, position: Int)

    fun setReviews(reviews: List<Review>)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)
}