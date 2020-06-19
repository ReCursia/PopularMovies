package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.DiscoverMoviesResponse
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.utils.Mapper

class DiscoverMovieResponseToMovieMapper(
    private val mapper: MovieModelToEntityMapper
) : Mapper<DiscoverMoviesResponse, List<Movie>>() {

    override fun transform(response: DiscoverMoviesResponse): List<Movie> {
        return mapper.transform(response.movies)
    }
}
