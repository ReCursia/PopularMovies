package com.example.popularmovies.models.database;

import android.arch.persistence.room.Dao;

@Dao
public interface GenreDao {
    /*
    @Query("SELECT * FROM genres WHERE movieId == :movieId")
    Single<List<Genre>> getGenresById(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenres(List<Genre> genres);

    @Delete
    void deleteGenres(List<Genre> genres);
    */
}
