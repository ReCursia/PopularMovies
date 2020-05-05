package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.ReviewsResponse
import com.recursia.popularmovies.domain.models.Review
import com.recursia.popularmovies.utils.Mapper

class ReviewsResponseToReviewMapper(private val mapper: ReviewNetworkToEntityModelMapper) : Mapper<ReviewsResponse, List<Review>>() {
    override fun transform(reviewsResponse: ReviewsResponse) = mapper.transform(reviewsResponse.reviews)
}
