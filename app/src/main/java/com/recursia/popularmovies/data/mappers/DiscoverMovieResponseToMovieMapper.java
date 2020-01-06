package com.recursia.popularmovies.data.mappers;

import com.recursia.popularmovies.data.models.DiscoverMoviesResponse;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.utils.Mapper;

import java.util.List;

public class DiscoverMovieResponseToMovieMapper extends Mapper<DiscoverMoviesResponse, List<Movie>> {
    private final MovieDatabaseModelToEntityMapper mapper;

    public DiscoverMovieResponseToMovieMapper(MovieDatabaseModelToEntityMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Movie> transform(DiscoverMoviesResponse response) {
        return mapper.transform(response.getMovies());
    }

}
