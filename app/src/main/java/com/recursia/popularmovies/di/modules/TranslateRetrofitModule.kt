package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.data.network.TranslateApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [RetrofitAdaptersModule::class])
class TranslateRetrofitModule {
    @Provides
    @Singleton
    internal fun translateApi(@Named("translateInstance") retrofit: Retrofit): TranslateApi {
        return retrofit.create(TranslateApi::class.java)
    }

    @Provides
    @Singleton
    @Named("translateInstance")
    internal fun retrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build()
    }

    companion object {
        private const val BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/"
    }
}
