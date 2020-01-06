package com.recursia.popularmovies.data.network.mappers;

import com.recursia.popularmovies.data.db.mappers.MovieDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.models.DiscoverMoviesResponse;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.utils.Mapper;

import java.util.List;

public class DiscoverMovieResponseToMovieMapper extends Mapper<DiscoverMoviesResponse, List<Movie>> {
    private MovieDatabaseModelToEntityMapper mapper;

    public DiscoverMovieResponseToMovieMapper(MovieDatabaseModelToEntityMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Movie> transform(DiscoverMoviesResponse response) {
        return mapper.transform(response.getMovies());
    }
}
