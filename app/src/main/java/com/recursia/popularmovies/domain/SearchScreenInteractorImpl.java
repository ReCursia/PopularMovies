package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Single;

public class SearchScreenInteractorImpl implements SearchScreenInteractor {

    private final MoviesRepository moviesRepository;

    public SearchScreenInteractorImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Single<List<Movie>> getMoviesByQuery(String query, int page, String language) {
        return moviesRepository.getMoviesByQuery(query, page, language);
    }

}
