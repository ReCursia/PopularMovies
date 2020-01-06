package com.recursia.popularmovies.data.network.mappers;

import com.recursia.popularmovies.data.db.mappers.CastDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.models.CreditsResponse;
import com.recursia.popularmovies.domain.models.Cast;
import com.recursia.popularmovies.utils.Mapper;

import java.util.List;

public class CreditsResponseToCastMapper extends Mapper<CreditsResponse, List<Cast>> {
    private final CastDatabaseModelToEntityMapper mapper;

    public CreditsResponseToCastMapper(CastDatabaseModelToEntityMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Cast> transform(CreditsResponse creditsResponse) {
        return mapper.transform(creditsResponse.getCast());
    }
}

