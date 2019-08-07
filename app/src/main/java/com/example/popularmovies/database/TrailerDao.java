package com.example.popularmovies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.popularmovies.pojo.Trailer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TrailerDao {

    @Query("SELECT * FROM trailers")
    Flowable<List<Trailer>> getAllTrailers();

    @Query("SELECT * FROM trailers WHERE id == :movieId")
    Single<List<Trailer>> getTrailersById(int movieId);

    @Query("DELETE FROM trailers")
    void deleteAllTrailers();

    @Insert
    void insertTrailer(Trailer trailer);

    @Insert
    void insertTrailers(List<Trailer> trailers);

    @Delete
    void deleteTrailer(Trailer trailer);

    @Delete
    void deleteTrailers(List<Trailer> trailers);

}
