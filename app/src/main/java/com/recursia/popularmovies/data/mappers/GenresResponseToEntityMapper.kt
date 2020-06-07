package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.GenresResponse
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.utils.Mapper

class GenresResponseToEntityMapper(
        private val mapper: GenreModelToEntityMapper
) : Mapper<GenresResponse, List<Genre>>() {
    override fun transform(genresResponse: GenresResponse): List<Genre> {
        return mapper.transform(genresResponse.genres)
    }
}
