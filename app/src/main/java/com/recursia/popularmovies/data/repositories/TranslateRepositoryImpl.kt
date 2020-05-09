package com.recursia.popularmovies.data.repositories

import com.recursia.popularmovies.data.network.TranslateApi
import com.recursia.popularmovies.domain.TranslateRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TranslateRepositoryImpl(
        private val translateApi: TranslateApi
) : TranslateRepository {
    override fun translateText(text: String, lang: String): Single<String> {
        return translateApi.getTranslate(text, lang)
                .subscribeOn(Schedulers.io())
                .map { it.text.firstOrNull() }
    }
}
