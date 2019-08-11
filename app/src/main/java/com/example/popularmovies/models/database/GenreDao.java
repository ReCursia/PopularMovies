package com.example.popularmovies.models.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.popularmovies.models.pojo.Genre;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM genres WHERE movieId == :movieId")
    Single<List<Genre>> getGenresById(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenres(List<Genre> genres);

    @Delete
    void deleteGenres(List<Genre> genres);

}
