package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.MovieTrailersResponse
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.utils.Mapper

class MovieTrailersResponseToTrailersMapper(
        private val mapper: TrailerModelToEntityMapper
) : Mapper<MovieTrailersResponse, List<Trailer>>() {

    override fun transform(movieTrailersResponse: MovieTrailersResponse): List<Trailer> {
        return mapper.transform(movieTrailersResponse.trailers)
    }
}
