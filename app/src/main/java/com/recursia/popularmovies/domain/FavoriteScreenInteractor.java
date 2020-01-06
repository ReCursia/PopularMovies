package com.recursia.popularmovies.domain;

import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface FavoriteScreenInteractor {

    Flowable<List<Movie>> getAllFavoriteMovies();

}
