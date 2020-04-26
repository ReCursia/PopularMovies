package com.recursia.popularmovies.data.repositories

import com.recursia.popularmovies.data.mappers.ReviewNetworkToEntityModelMapper
import com.recursia.popularmovies.data.network.TranslateApi
import com.recursia.popularmovies.domain.TranslateRepository
import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Single

class TranslateRepositoryImpl(
        private val translateApi: TranslateApi,
        private val mapper: ReviewNetworkToEntityModelMapper
) : TranslateRepository {

    override fun getTranslate(text: String, lang: String): Single<Review> {
        TODO("Not yet implemented")
    }
}