package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Single

interface TranslateRepository {
    fun translateReview(review: Review, lang: String): Single<Review>
}
