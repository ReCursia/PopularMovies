package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.MovieModel
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import com.recursia.popularmovies.utils.Mapper

class MovieModelToEntityMapper(
        private val genreDatabaseModelToEntityMapper: GenreModelToEntityMapper
) : Mapper<MovieModel, Movie>() {

    override fun transform(movieDatabaseModel: MovieModel): Movie {
        val movie = Movie()
        movie.genres = genreDatabaseModelToEntityMapper.transform(movieDatabaseModel.genres)
        movie.backdropPath = movieDatabaseModel.backdropPath
        movie.id = movieDatabaseModel.id
        movie.overview = movieDatabaseModel.overview
        movie.posterPath = movieDatabaseModel.posterPath
        movie.releaseDate = movieDatabaseModel.releaseDate
        movie.title = movieDatabaseModel.title
        movie.voteAverage = movieDatabaseModel.voteAverage
        movie.status = MovieStatus.UNKNOWN
        return movie
    }
}
