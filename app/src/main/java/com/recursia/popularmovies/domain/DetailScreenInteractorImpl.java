package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DetailScreenInteractorImpl implements DetailScreenInteractor {

    private final MoviesRepository moviesRepository;

    public DetailScreenInteractorImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Single<List<Movie>> getMovieRecommendations(int movieId, int page, String language) {
        return moviesRepository.getMovieRecommendations(movieId, page, language);
    }

    @Override
    public Single<Movie> getMovieById(int movieId, String language) {
        return moviesRepository.getMovieById(movieId, language);
    }

    @Override
    public Completable makeFavoriteMovie(Movie movie) {
        return moviesRepository.makeFavoriteMovie(movie);
    }

    @Override
    public Completable removeFavoriteMovie(Movie movie) {
        return moviesRepository.removeFavoriteMovie(movie);
    }

}
