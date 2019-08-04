package com.example.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.popularmovies.pojo.FavoriteMovie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_movies")
    LiveData<List<FavoriteMovie>> getAllFavoriteMovies();

    @Query("SELECT * FROM favorite_movies WHERE id == :movieId")
    FavoriteMovie getMovieById(int movieId);

    @Query("DELETE FROM favorite_movies")
    void deleteAllMovies();

    @Insert
    void insertMovie(FavoriteMovie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<FavoriteMovie> movies);

    @Delete
    void deleteMovie(FavoriteMovie movie);
}
