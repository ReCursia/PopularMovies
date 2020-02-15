package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.CreditsResponse
import com.recursia.popularmovies.domain.models.Cast
import com.recursia.popularmovies.utils.Mapper

class CreditsResponseToCastMapper(private val mapper: CastDatabaseModelToEntityMapper) : Mapper<CreditsResponse, List<Cast>>() {

    override fun transform(creditsResponse: CreditsResponse): List<Cast> {
        return mapper.transform(creditsResponse.cast)
    }

}

