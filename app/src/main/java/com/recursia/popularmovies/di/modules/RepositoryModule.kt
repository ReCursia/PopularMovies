package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.data.db.MovieDao
import com.recursia.popularmovies.data.mappers.EntityToMovieExtraDatabaseModelMapper
import com.recursia.popularmovies.data.mappers.MovieDatabaseModelToEntityMapper
import com.recursia.popularmovies.data.mappers.MovieExtraDatabaseModelToEntityMapper
import com.recursia.popularmovies.data.network.MoviesApi
import com.recursia.popularmovies.data.mappers.CreditsResponseToCastMapper
import com.recursia.popularmovies.data.mappers.DiscoverMovieResponseToMovieMapper
import com.recursia.popularmovies.data.mappers.MovieTrailersResponseToTrailersMapper
import com.recursia.popularmovies.data.repositories.MoviesRepositoryImpl
import com.recursia.popularmovies.domain.MoviesRepository

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module(includes = [RetrofitModule::class, RoomModule::class, MapperModule::class])
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
        entityToMovieExtraDatabaseModelMapper: EntityToMovieExtraDatabaseModelMapper
    ): MoviesRepository {
        return MoviesRepositoryImpl(
                movieDao,
                moviesApi,
                movieDatabaseModelToEntityMapper,
                discoverMovieResponseToMovieMapper,
                creditsResponseToCastMapper,
                movieTrailersResponseToTrailersMapper,
                movieExtraDatabaseModelToEntityMapper,
                entityToMovieExtraDatabaseModelMapper)
    }
}
