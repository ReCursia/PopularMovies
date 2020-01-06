package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Flowable;

public class FavoriteScreenInteractorImpl implements FavoriteScreenInteractor {

    private final MoviesRepository moviesRepository;

    public FavoriteScreenInteractorImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Flowable<List<Movie>> getAllFavoriteMovies() {
        return moviesRepository.getAllFavoriteMovies();
    }
}
