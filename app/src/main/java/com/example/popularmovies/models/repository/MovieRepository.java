package com.example.popularmovies.models.repository;

import com.example.popularmovies.models.pojo.Movie;

import java.util.Observable;

import io.reactivex.Single;

public interface MovieRepository {
    void saveMovie(Movie movie);
    void deleteMovie(Movie movie);
    Single<Movie> getMovieById(int id);
    void 
}
