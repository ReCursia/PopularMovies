package com.recursia.popularmovies.data.repositories

import com.recursia.popularmovies.data.network.TranslateApi
import com.recursia.popularmovies.domain.TranslateRepository
import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Class used to get text translate from Yandex Translate
 */
class TranslateRepositoryImpl(
        private val translateApi: TranslateApi // translate yandex api
) : TranslateRepository {
    /**
     * Translate review
     * @param review review
     * @param lang language to translate
     *
     * @return review or onError
     */
    override fun translateReview(review: Review, lang: String): Single<Review> {
        return translateApi.getTranslate(review.text!!, lang)
                .map {
                    review.text = it.text.first()
                    review
                }
                .subscribeOn(Schedulers.io())
    }
}
