package com.recursia.popularmovies.di.modules

import dagger.Module

@Module
class MapperModule {
    /*
    @Provides
    internal fun provideCastDatabaseModelToEntityMapper(): CastDatabaseModelToEntityMapper {
        return CastDatabaseModelToEntityMapper()
    }

    @Provides
    internal fun provideCreditsResponseToCaseMapper(mapper: CastDatabaseModelToEntityMapper): CreditsResponseToCastMapper {
        return CreditsResponseToCastMapper(mapper)
    }

    @Provides
    internal fun provideDiscoverMovieResponseToMovieMapper(mapper: MovieDatabaseModelToEntityMapper): DiscoverMovieResponseToMovieMapper {
        return DiscoverMovieResponseToMovieMapper(mapper)
    }

    @Provides
    internal fun provideGenreDatabaseModelToEntityMapper(): GenreDatabaseModelToEntityMapper {
        return GenreDatabaseModelToEntityMapper()
    }

    @Provides
    internal fun provideMovieDatabaseModelToEntityMapper(mapper: GenreDatabaseModelToEntityMapper): MovieDatabaseModelToEntityMapper {
        return MovieDatabaseModelToEntityMapper(mapper)
    }

    @Provides
    internal fun provideEntityToMovieDatabaseModelMapper(
            entityToCastDatabaseModelMapper: EntityToCastDatabaseModelMapper,
            entityToGenreDatabaseModelMapper: EntityToGenreDatabaseModelMapper,
            entityToTrailerDatabaseModelMapper: EntityToTrailerDatabaseModelMapper
    ): EntityToMovieExtraDatabaseModelMapper {
        return EntityToMovieExtraDatabaseModelMapper(entityToCastDatabaseModelMapper, entityToGenreDatabaseModelMapper, entityToTrailerDatabaseModelMapper)
    }

    @Provides
    internal fun provideEntityToTrailerDatabaseModelMapper(): EntityToTrailerDatabaseModelMapper {
        return EntityToTrailerDatabaseModelMapper()
    }

    @Provides
    internal fun provideEntityToGenreDatabaseModelMapper(): EntityToGenreDatabaseModelMapper {
        return EntityToGenreDatabaseModelMapper()
    }

    @Provides
    internal fun provideEntityToCastDatabaseModelMapper(): EntityToCastDatabaseModelMapper {
        return EntityToCastDatabaseModelMapper()
    }

    @Provides
    internal fun provideMovieTrailersResponseToTrailersMapper(mapper: TrailerDatabaseModelToEntityMapper): MovieTrailersResponseToTrailersMapper {
        return MovieTrailersResponseToTrailersMapper(mapper)
    }

    @Provides
    internal fun provideTrailerDatabaseModelToEntityMapper(): TrailerDatabaseModelToEntityMapper {
        return TrailerDatabaseModelToEntityMapper()
    }

    @Provides
    internal fun provideMovieExtraDatabaseModelToEntityMapper(
            genreDatabaseModelToEntityMapper: GenreDatabaseModelToEntityMapper,
            castDatabaseModelToEntityMapper: CastDatabaseModelToEntityMapper,
            trailerDatabaseModelToEntityMapper: TrailerDatabaseModelToEntityMapper
    ): MovieExtraDatabaseModelToEntityMapper {
        return MovieExtraDatabaseModelToEntityMapper(genreDatabaseModelToEntityMapper, castDatabaseModelToEntityMapper, trailerDatabaseModelToEntityMapper)
    }

    @Provides
    internal fun provideReviewNetworkModelToEntityMapper(): ReviewNetworkToEntityModelMapper {
        return ReviewNetworkToEntityModelMapper()
    }

    @Provides
    internal fun provideReviewsResponseToReviewMapper(
            reviewNetworkToEntityModelMapper: ReviewNetworkToEntityModelMapper
    ): ReviewsResponseToReviewMapper {
        return ReviewsResponseToReviewMapper(reviewNetworkToEntityModelMapper)
    }

     */
}
