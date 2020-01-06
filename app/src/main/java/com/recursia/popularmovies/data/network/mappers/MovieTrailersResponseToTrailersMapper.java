package com.recursia.popularmovies.data.network.mappers;

import com.recursia.popularmovies.data.db.mappers.TrailerDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.models.MovieTrailersResponse;
import com.recursia.popularmovies.domain.models.Trailer;
import com.recursia.popularmovies.utils.Mapper;

import java.util.List;

public class MovieTrailersResponseToTrailersMapper extends Mapper<MovieTrailersResponse, List<Trailer>> {
    private final TrailerDatabaseModelToEntityMapper mapper;

    public MovieTrailersResponseToTrailersMapper(TrailerDatabaseModelToEntityMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Trailer> transform(MovieTrailersResponse movieTrailersResponse) {
        return mapper.transform(movieTrailersResponse.getTrailers());
    }

}
