package com.recursia.popularmovies.di.modules;

import com.recursia.popularmovies.data.db.mappers.CastDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.db.mappers.EntityToCastDatabaseModelMapper;
import com.recursia.popularmovies.data.db.mappers.EntityToGenreDatabaseModelMapper;
import com.recursia.popularmovies.data.db.mappers.EntityToMovieExtraDatabaseModelMapper;
import com.recursia.popularmovies.data.db.mappers.EntityToTrailerDatabaseModelMapper;
import com.recursia.popularmovies.data.db.mappers.GenreDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.db.mappers.MovieDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.db.mappers.MovieExtraDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.db.mappers.TrailerDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.network.mappers.CreditsResponseToCastMapper;
import com.recursia.popularmovies.data.network.mappers.DiscoverMovieResponseToMovieMapper;
import com.recursia.popularmovies.data.network.mappers.MovieTrailersResponseToTrailersMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class MapperModule {
    @Provides
    CastDatabaseModelToEntityMapper provideCastDatabaseModelToEntityMapper() {
        return new CastDatabaseModelToEntityMapper();
    }

    @Provides
    CreditsResponseToCastMapper provideCreditsResponseToCaseMapper(CastDatabaseModelToEntityMapper mapper) {
        return new CreditsResponseToCastMapper(mapper);
    }

    @Provides
    DiscoverMovieResponseToMovieMapper provideDiscoverMovieResponseToMovieMapper(MovieDatabaseModelToEntityMapper mapper) {
        return new DiscoverMovieResponseToMovieMapper(mapper);
    }

    @Provides
    GenreDatabaseModelToEntityMapper provideGenreDatabaseModelToEntityMapper() {
        return new GenreDatabaseModelToEntityMapper();
    }

    @Provides
    MovieDatabaseModelToEntityMapper provideMovieDatabaseModelToEntityMapper(GenreDatabaseModelToEntityMapper mapper) {
        return new MovieDatabaseModelToEntityMapper(mapper);
    }

    @Provides
    EntityToMovieExtraDatabaseModelMapper provideEntityToMovieDatabaseModelMapper(EntityToCastDatabaseModelMapper entityToCastDatabaseModelMapper,
                                                                                  EntityToGenreDatabaseModelMapper entityToGenreDatabaseModelMapper,
                                                                                  EntityToTrailerDatabaseModelMapper entityToTrailerDatabaseModelMapper) {
        return new EntityToMovieExtraDatabaseModelMapper(entityToCastDatabaseModelMapper, entityToGenreDatabaseModelMapper, entityToTrailerDatabaseModelMapper);
    }

    @Provides
    EntityToTrailerDatabaseModelMapper provideEntityToTrailerDatabaseModelMapper() {
        return new EntityToTrailerDatabaseModelMapper();
    }

    @Provides
    EntityToGenreDatabaseModelMapper provideEntityToGenreDatabaseModelMapper() {
        return new EntityToGenreDatabaseModelMapper();
    }

    @Provides
    EntityToCastDatabaseModelMapper provideEntityToCastDatabaseModelMapper() {
        return new EntityToCastDatabaseModelMapper();
    }

    @Provides
    MovieTrailersResponseToTrailersMapper provideMovieTrailersResponseToTrailersMapper(TrailerDatabaseModelToEntityMapper mapper) {
        return new MovieTrailersResponseToTrailersMapper(mapper);
    }

    @Provides
    TrailerDatabaseModelToEntityMapper provideTrailerDatabaseModelToEntityMapper() {
        return new TrailerDatabaseModelToEntityMapper();
    }

    @Provides
    MovieExtraDatabaseModelToEntityMapper provideMovieExtraDatabaseModelToEntityMapper(GenreDatabaseModelToEntityMapper genreDatabaseModelToEntityMapper,
                                                                                       CastDatabaseModelToEntityMapper castDatabaseModelToEntityMapper,
                                                                                       TrailerDatabaseModelToEntityMapper trailerDatabaseModelToEntityMapper) {
        return new MovieExtraDatabaseModelToEntityMapper(genreDatabaseModelToEntityMapper, castDatabaseModelToEntityMapper, trailerDatabaseModelToEntityMapper);
    }
}
