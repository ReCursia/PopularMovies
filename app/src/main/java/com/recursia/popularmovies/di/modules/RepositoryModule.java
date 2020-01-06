package com.recursia.popularmovies.di.modules;

import com.recursia.popularmovies.data.db.MovieDao;
import com.recursia.popularmovies.data.db.mappers.EntityToMovieExtraDatabaseModelMapper;
import com.recursia.popularmovies.data.db.mappers.MovieDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.db.mappers.MovieExtraDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.network.MoviesApi;
import com.recursia.popularmovies.data.network.mappers.CreditsResponseToCastMapper;
import com.recursia.popularmovies.data.network.mappers.DiscoverMovieResponseToMovieMapper;
import com.recursia.popularmovies.data.network.mappers.MovieTrailersResponseToTrailersMapper;
import com.recursia.popularmovies.data.repositories.MoviesRepositoryImpl;
import com.recursia.popularmovies.domain.MoviesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RetrofitModule.class, RoomModule.class, MapperModule.class})
public class RepositoryModule {

    @Provides
    @Singleton
    MoviesRepository provideMoviesRepository(MovieDao movieDao,
                                             MoviesApi moviesApi,
                                             MovieDatabaseModelToEntityMapper movieDatabaseModelToEntityMapper,
                                             DiscoverMovieResponseToMovieMapper discoverMovieResponseToMovieMapper,
                                             CreditsResponseToCastMapper creditsResponseToCastMapper,
                                             MovieTrailersResponseToTrailersMapper movieTrailersResponseToTrailersMapper,
                                             MovieExtraDatabaseModelToEntityMapper movieExtraDatabaseModelToEntityMapper,
                                             EntityToMovieExtraDatabaseModelMapper entityToMovieExtraDatabaseModelMapper) {
        return new MoviesRepositoryImpl(
                movieDao,
                moviesApi,
                movieDatabaseModelToEntityMapper,
                discoverMovieResponseToMovieMapper,
                creditsResponseToCastMapper,
                movieTrailersResponseToTrailersMapper,
                movieExtraDatabaseModelToEntityMapper,
                entityToMovieExtraDatabaseModelMapper);
    }
}
