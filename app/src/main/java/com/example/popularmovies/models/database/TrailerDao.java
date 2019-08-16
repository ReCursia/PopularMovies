package com.example.popularmovies.models.database;

import android.arch.persistence.room.Dao;

@Dao
public interface TrailerDao {
    /*
    @Query("SELECT * FROM trailers WHERE movieId == :movieId")
    Single<List<Trailer>> getTrailersById(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrailers(List<Trailer> trailers);

    @Delete
    void deleteTrailers(List<Trailer> trailers);
    */
}
