package com.recursia.popularmovies.data.network

import com.recursia.popularmovies.data.models.TranslateModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * This interface describes translate API of Yandex translate
 */
interface TranslateApi {

    /**
     * Get translate of text
     * @param text plain text
     * @param lang language
     *
     * @return translated text response
     */
    @GET("translate?key=$API_KEY")
    fun getTranslate(@Query("text") text: String, @Query("lang") lang: String): Single<TranslateModel>

    companion object {
        //api key to work with
        private const val API_KEY = "trnsl.1.1.20191022T132422Z.e2514f4cb9505689.ad6cb58598cfcfc04e982319ce1558fa6f50c224"
    }
}
