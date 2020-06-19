package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.data.network.MoviesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [RetrofitAdaptersModule::class])
class MoviesRetrofitModule {

    @Provides
    @Singleton
    internal fun moviesApi(@Named("moviesInstance") retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    @Named("moviesInstance")
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
        private const val BASE_URL = "https://api.themoviedb.org/"
    }
}
