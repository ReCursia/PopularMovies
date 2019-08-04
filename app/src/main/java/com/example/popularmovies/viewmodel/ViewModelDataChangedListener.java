package com.example.popularmovies.viewmodel;

import com.example.popularmovies.pojo.FavoriteMovie;
import com.example.popularmovies.pojo.Movie;

import java.util.List;

public interface ViewModelDataChangedListener {
    void moviesDataChanged(List<Movie> movieList);

    void favoriteMoviesDataChanged(List<FavoriteMovie> favoriteMovieList);
}
