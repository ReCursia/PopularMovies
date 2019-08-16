package com.example.popularmovies.models.database;

import android.arch.persistence.room.Dao;

@Dao
public interface MovieDao {
    /*
    @Query("SELECT * FROM movies")
    Flowable<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id == :movieId")
    Single<Movie> getMovieById(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
    */
}

