package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DetailScreenInteractor {

    Single<Movie> getMovieById(int movieId, String language);

    Completable makeFavoriteMovie(Movie movie);

    Completable removeFavoriteMovie(Movie movie);

    Single<List<Movie>> getMovieRecommendations(int movieId, int page, String language);

}
