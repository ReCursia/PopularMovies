package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.MovieDatabaseModel
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.utils.Mapper

class MovieDatabaseModelToEntityMapper(private val genreDatabaseModelToEntityMapper: GenreDatabaseModelToEntityMapper) : Mapper<MovieDatabaseModel, Movie>() {

    override fun transform(movieDatabaseModel: MovieDatabaseModel): Movie {
        val movie = Movie()
        movie.genres = genreDatabaseModelToEntityMapper.transform(movieDatabaseModel.genres)
        movie.budget = movieDatabaseModel.budget
        movie.backdropPath = movieDatabaseModel.backdropPath
        movie.isFavorite = movieDatabaseModel.isFavorite
        movie.id = movieDatabaseModel.id
        movie.originalTitle = movieDatabaseModel.originalTitle
        movie.overview = movieDatabaseModel.overview
        movie.popularity = movieDatabaseModel.popularity
        movie.originalLanguage = movieDatabaseModel.originalLanguage
        movie.posterPath = movieDatabaseModel.posterPath
        movie.releaseDate = movieDatabaseModel.releaseDate
        movie.runtime = movieDatabaseModel.runtime
        movie.status = movieDatabaseModel.status
        movie.tagline = movieDatabaseModel.tagline
        movie.title = movieDatabaseModel.title
        movie.isVideo = movieDatabaseModel.isVideo
        movie.voteAverage = movieDatabaseModel.voteAverage
        movie.voteCount = movieDatabaseModel.voteCount
        return movie
    }
}
