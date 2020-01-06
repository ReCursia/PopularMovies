package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Single;

public class MoviesListInteractorImpl implements MoviesListInteractor {

    private final MoviesRepository moviesRepository;

    public MoviesListInteractorImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Single<List<Movie>> discoverMovies(String sortBy, int page, int voteCount, String language) {
        return moviesRepository.discoverMovies(sortBy, page, voteCount, language);
    }

}
