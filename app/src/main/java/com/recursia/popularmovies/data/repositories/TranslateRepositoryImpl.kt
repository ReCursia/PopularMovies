package com.recursia.popularmovies.data.repositories

import com.recursia.popularmovies.data.network.TranslateApi
import com.recursia.popularmovies.domain.TranslateRepository
import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TranslateRepositoryImpl(
        private val translateApi: TranslateApi
) : TranslateRepository {
    override fun translateReview(review: Review, lang: String): Single<Review> {
        return translateApi.getTranslate(review.text!!, lang)
                .map {
                    review.text = it.text.first()
                    review
                }
                .subscribeOn(Schedulers.io())
    }
}
