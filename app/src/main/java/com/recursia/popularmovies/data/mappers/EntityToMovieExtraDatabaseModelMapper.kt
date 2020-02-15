package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.MovieDatabaseModel
import com.recursia.popularmovies.data.models.MovieExtraDatabaseModel
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.utils.Mapper

class EntityToMovieExtraDatabaseModelMapper(private val entityToCastDatabaseModelMapper: EntityToCastDatabaseModelMapper,
                                            private val entityToGenreDatabaseModelMapper: EntityToGenreDatabaseModelMapper,
                                            private val entityToTrailerDatabaseModelMapper: EntityToTrailerDatabaseModelMapper) : Mapper<Movie, MovieExtraDatabaseModel>() {

    override fun transform(movie: Movie): MovieExtraDatabaseModel {
        val movieExtraDatabaseModel = MovieExtraDatabaseModel()
        movieExtraDatabaseModel.cast = entityToCastDatabaseModelMapper.transform(movie.casts)
        movieExtraDatabaseModel.genres = entityToGenreDatabaseModelMapper.transform(movie.genres)
        movieExtraDatabaseModel.trailers = entityToTrailerDatabaseModelMapper.transform(movie.trailers)
        //Movie
        val movieDatabaseModel = MovieDatabaseModel()
        movieDatabaseModel.isFavorite = movie.isFavorite
        movieDatabaseModel.voteCount = movie.voteCount
        movieDatabaseModel.voteAverage = movie.voteAverage
        movieDatabaseModel.isVideo = movie.isVideo
        movieDatabaseModel.title = movie.title
        movieDatabaseModel.budget = movie.budget
        movieDatabaseModel.backdropPath = movie.backdropPath
        movieDatabaseModel.id = movie.id
        movieDatabaseModel.originalTitle = movie.originalTitle
        movieDatabaseModel.popularity = movie.popularity
        movieDatabaseModel.originalLanguage = movie.originalLanguage
        movieDatabaseModel.overview = movie.overview
        movieDatabaseModel.posterPath = movie.posterPath
        movieDatabaseModel.releaseDate = movie.releaseDate
        movieDatabaseModel.status = movie.status
        movieDatabaseModel.tagline = movie.tagline
        movieDatabaseModel.runtime = movie.runtime
        movieDatabaseModel.originalTitle = movie.originalTitle
        movieExtraDatabaseModel.movie = movieDatabaseModel
        return movieExtraDatabaseModel
    }
}
