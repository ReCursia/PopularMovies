package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Single;

public interface MoviesListInteractor {

    Single<List<Movie>> discoverMovies(String sortBy, int page, int voteCount, String language);

}
