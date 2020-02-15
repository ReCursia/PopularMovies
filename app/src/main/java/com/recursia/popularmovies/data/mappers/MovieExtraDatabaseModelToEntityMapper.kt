package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.MovieExtraDatabaseModel
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.utils.Mapper

class MovieExtraDatabaseModelToEntityMapper(private val genreDatabaseModelToEntityMapper: GenreDatabaseModelToEntityMapper,
                                            private val castDatabaseModelToEntityMapper: CastDatabaseModelToEntityMapper,
                                            private val trailerDatabaseModelToEntityMapper: TrailerDatabaseModelToEntityMapper) : Mapper<MovieExtraDatabaseModel, Movie>() {

    override fun transform(movieExtraDatabaseModel: MovieExtraDatabaseModel): Movie {
        val movie = Movie()
        movie.genres = genreDatabaseModelToEntityMapper.transform(movieExtraDatabaseModel.genres)
        movie.trailers = trailerDatabaseModelToEntityMapper.transform(movieExtraDatabaseModel.trailers)
        movie.casts = castDatabaseModelToEntityMapper.transform(movieExtraDatabaseModel.cast)
        //Movie
        val movieDatabaseModel = movieExtraDatabaseModel.movie
        movie.isFavorite = movieDatabaseModel.isFavorite
        movie.voteCount = movieDatabaseModel.voteCount
        movie.voteAverage = movieDatabaseModel.voteAverage
        movie.isVideo = movieDatabaseModel.isVideo
        movie.title = movieDatabaseModel.title
        movie.budget = movieDatabaseModel.budget
        movie.backdropPath = movieDatabaseModel.backdropPath
        movie.id = movieDatabaseModel.id
        movie.originalTitle = movieDatabaseModel.originalTitle
        movie.popularity = movieDatabaseModel.popularity
        movie.originalLanguage = movieDatabaseModel.originalLanguage
        movie.overview = movieDatabaseModel.overview
        movie.posterPath = movieDatabaseModel.posterPath
        movie.releaseDate = movieDatabaseModel.releaseDate
        movie.status = movieDatabaseModel.status
        movie.tagline = movieDatabaseModel.tagline
        movie.runtime = movieDatabaseModel.runtime
        movie.originalTitle = movieDatabaseModel.originalTitle
        return movie
    }
}
