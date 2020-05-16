package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.data.mappers.*
import com.recursia.popularmovies.data.network.MoviesApi
import com.recursia.popularmovies.data.network.TranslateApi
import com.recursia.popularmovies.data.repositories.AccountRepositoryImpl
import com.recursia.popularmovies.data.repositories.MoviesRepositoryImpl
import com.recursia.popularmovies.data.repositories.TranslateRepositoryImpl
import com.recursia.popularmovies.domain.AccountRepository
import com.recursia.popularmovies.domain.MoviesRepository
import com.recursia.popularmovies.domain.TranslateRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    MoviesRetrofitModule::class,
    TranslateRetrofitModule::class,
    MapperModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideMoviesRepository(
            moviesApi: MoviesApi,
            discoverMovieResponseToMovieMapper: DiscoverMovieResponseToMovieMapper,
            creditsResponseToCastMapper: CreditsResponseToCastMapper,
            movieTrailersResponseToTrailersMapper: MovieTrailersResponseToTrailersMapper,
            reviewsResponseToReviewMapper: ReviewsResponseToReviewMapper,
            movieModelToEntityMapper: MovieModelToEntityMapper
    ): MoviesRepository {
        return MoviesRepositoryImpl(
                moviesApi,
                discoverMovieResponseToMovieMapper,
                creditsResponseToCastMapper,
                movieTrailersResponseToTrailersMapper,
                reviewsResponseToReviewMapper,
                movieModelToEntityMapper
        )
    }


    @Provides
    @Singleton
    internal fun provideAccountRepository(): AccountRepository {
        return AccountRepositoryImpl()
    }

    @Provides
    @Singleton
    internal fun provideTranslateRepository(translateApi: TranslateApi): TranslateRepository {
        return TranslateRepositoryImpl(translateApi)
    }

}
