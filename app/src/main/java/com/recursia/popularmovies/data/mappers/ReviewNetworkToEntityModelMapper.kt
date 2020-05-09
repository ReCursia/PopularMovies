package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.ReviewNetworkModel
import com.recursia.popularmovies.domain.models.Review
import com.recursia.popularmovies.utils.Mapper

class ReviewNetworkToEntityModelMapper : Mapper<ReviewNetworkModel, Review>() {
    override fun transform(reviewNetworkModel: ReviewNetworkModel): Review {
        val review = Review()
        review.author = reviewNetworkModel.author
        review.isFromReviewSite = true
        review.text = reviewNetworkModel.content
        review.id = reviewNetworkModel.id
        review.url = reviewNetworkModel.url
        return review
    }
}
