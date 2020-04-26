package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.data.db.MovieDao
import com.recursia.popularmovies.data.mappers.*
import com.recursia.popularmovies.data.network.MoviesApi
import com.recursia.popularmovies.data.network.TranslateApi
import com.recursia.popularmovies.data.repositories.MoviesRepositoryImpl
import com.recursia.popularmovies.data.repositories.TranslateRepositoryImpl
import com.recursia.popularmovies.domain.MoviesRepository
import com.recursia.popularmovies.domain.TranslateRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    MoviesRetrofitModule::class,
    TranslateRetrofitModule::class,
    RoomModule::class,
    MapperModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideMoviesRepository(
            movieDao: MovieDao,
            moviesApi: MoviesApi,
            movieDatabaseModelToEntityMapper: MovieDatabaseModelToEntityMapper,
            discoverMovieResponseToMovieMapper: DiscoverMovieResponseToMovieMapper,
            creditsResponseToCastMapper: CreditsResponseToCastMapper,
            movieTrailersResponseToTrailersMapper: MovieTrailersResponseToTrailersMapper,
            movieExtraDatabaseModelToEntityMapper: MovieExtraDatabaseModelToEntityMapper,
            entityToMovieExtraDatabaseModelMapper: EntityToMovieExtraDatabaseModelMapper,
            reviewsResponseToReviewMapper: ReviewsResponseToReviewMapper
    ): MoviesRepository {
        return MoviesRepositoryImpl(
                movieDao,
                moviesApi,
                movieDatabaseModelToEntityMapper,
                discoverMovieResponseToMovieMapper,
                creditsResponseToCastMapper,
                movieTrailersResponseToTrailersMapper,
                movieExtraDatabaseModelToEntityMapper,
                entityToMovieExtraDatabaseModelMapper,
                reviewsResponseToReviewMapper)
    }

    @Provides
    @Singleton
    internal fun provideTranslateRepository(
            mapper: ReviewNetworkToEntityModelMapper,
            translateApi: TranslateApi
    ): TranslateRepository {
        return TranslateRepositoryImpl(translateApi, mapper)
    }
}
