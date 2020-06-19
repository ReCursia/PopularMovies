package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.data.mappers.*
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    internal fun provideCastDatabaseModelToEntityMapper(): CastModelToEntityMapper {
        return CastModelToEntityMapper()
    }

    @Provides
    internal fun provideCreditsResponseToCaseMapper(mapper: CastModelToEntityMapper): CreditsResponseToCastMapper {
        return CreditsResponseToCastMapper(mapper)
    }

    @Provides
    internal fun provideDiscoverMovieResponseToMovieMapper(mapper: MovieModelToEntityMapper): DiscoverMovieResponseToMovieMapper {
        return DiscoverMovieResponseToMovieMapper(mapper)
    }

    @Provides
    internal fun provideGenreDatabaseModelToEntityMapper(): GenreModelToEntityMapper {
        return GenreModelToEntityMapper()
    }

    @Provides
    internal fun provideMovieDatabaseModelToEntityMapper(mapper: GenreModelToEntityMapper): MovieModelToEntityMapper {
        return MovieModelToEntityMapper(mapper)
    }

    @Provides
    internal fun provideEntityToTrailerDatabaseModelMapper(): EntityToTrailerModelMapper {
        return EntityToTrailerModelMapper()
    }

    @Provides
    internal fun provideEntityToGenreDatabaseModelMapper(): EntityToGenreModelMapper {
        return EntityToGenreModelMapper()
    }

    @Provides
    internal fun provideEntityToCastDatabaseModelMapper(): EntityToCastModelMapper {
        return EntityToCastModelMapper()
    }

    @Provides
    internal fun provideMovieTrailersResponseToTrailersMapper(mapper: TrailerModelToEntityMapper): MovieTrailersResponseToTrailersMapper {
        return MovieTrailersResponseToTrailersMapper(mapper)
    }

    @Provides
    internal fun provideTrailerDatabaseModelToEntityMapper(): TrailerModelToEntityMapper {
        return TrailerModelToEntityMapper()
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

    @Provides
    internal fun provideGenresResponseToEntityMapper(
        genreModelToEntityMapper: GenreModelToEntityMapper
    ): GenresResponseToEntityMapper {
        return GenresResponseToEntityMapper(genreModelToEntityMapper)
    }
}
