package com.example.popularmovies.models.repository;

import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.Trailer;

import java.util.List;

import io.reactivex.Single;

public interface MovieRepository {

    void saveMovie(Movie movie);

    void deleteMovie(Movie movie);

    void saveTrailers(List<Trailer> trailers, int movieId);

    void deleteTrailers(List<Trailer> trailers);

    void saveGenres(List<Genre> genres, int movieId);

    void deleteGenres(List<Genre> genres);

    Single<Movie> getMovieById(int movieId);

    Single<List<Trailer>> getTrailersById(int movieId);

    Single<List<Genre>> getGenresById(int movieId);

}
