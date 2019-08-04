package com.example.popularmovies.viewmodel;

import com.example.popularmovies.pojo.FavoriteMovie;
import com.example.popularmovies.pojo.Movie;

import java.util.List;

public interface MovieViewModel {

    Movie getMovieById(int id);

    FavoriteMovie getFavoriteMovieById(int id);

    void setDataChangedListener(ViewModelDataChangedListener listener);

    void deleteAllMovies();

    void insertMovie(Movie movie);

    void insertFavoriteMovie(FavoriteMovie movie);

    void getAllMovies();

    void insertFavoriteMovies(List<FavoriteMovie> movies);

    void deleteAllFavoriteMovies();

    void deleteFavoriteMovie(FavoriteMovie movie);

    void insertMovies(List<Movie> movies);

    void deleteMovie(Movie movie);
}
