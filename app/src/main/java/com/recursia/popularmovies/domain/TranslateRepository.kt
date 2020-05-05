package com.recursia.popularmovies.domain

import io.reactivex.Single

interface TranslateRepository {
    // TODO make it to use review logic, or it should use only translate logic
    fun translateText(text: String, lang: String): Single<String>
}
