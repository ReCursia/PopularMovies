package com.example.popularmovies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.popularmovies.pojo.Trailer;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface TrailerDao {

    @Query("SELECT * FROM trailers WHERE movieId == :movieId")
    Single<List<Trailer>> getTrailersById(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrailers(List<Trailer> trailers);

    @Delete
    void deleteTrailers(List<Trailer> trailers);

}
