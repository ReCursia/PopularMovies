package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Flowable;

public interface FavoriteScreenInteractor {

    Flowable<List<Movie>> getAllFavoriteMovies();

}
