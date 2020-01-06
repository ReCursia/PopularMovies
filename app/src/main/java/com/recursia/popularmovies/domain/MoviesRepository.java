package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface MoviesRepository {

    Single<List<Movie>> discoverMovies(String sortBy, int page, int voteCount, String language);

    Flowable<List<Movie>> getAllFavoriteMovies();

    Single<Movie> getMovieById(int movieId, String language);

    Single<List<Movie>> getMoviesByQuery(String query, int page, String language);

    Single<List<Movie>> getMovieRecommendations(int movieId, int page, String language);

    Completable makeFavoriteMovie(Movie movie);

    Completable removeFavoriteMovie(Movie movie);

}
