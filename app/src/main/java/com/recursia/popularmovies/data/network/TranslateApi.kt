package com.recursia.popularmovies.data.network

import com.recursia.popularmovies.data.models.TranslateModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateApi {

    @GET("translate?key=$API_KEY")
    fun getTranslate(@Query("text") text: String, @Query("lang") lang: String): Single<TranslateModel>

    companion object {
        private const val API_KEY = "trnsl.1.1.20191022T132422Z.e2514f4cb9505689.ad6cb58598cfcfc04e982319ce1558fa6f50c224"
    }
}
