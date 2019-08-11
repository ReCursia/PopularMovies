package com.example.popularmovies.models.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.popularmovies.models.pojo.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    Flowable<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id == :movieId")
    Single<Movie> getMovieById(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

}

