package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Single;

public interface SearchScreenInteractor {

    Single<List<Movie>> getMoviesByQuery(String query, int page, String language);

}
