package com.recursia.popularmovies.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitAdaptersModule {
    @Provides
    @Singleton
    internal fun rxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    internal fun gsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}
