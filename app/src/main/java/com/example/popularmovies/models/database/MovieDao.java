package com.example.popularmovies.models.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.popularmovies.models.pojo.Cast;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.MovieExtra;
import com.example.popularmovies.models.pojo.Trailer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    Flowable<List<MovieExtra>> getAllMovies();

    @Transaction
    @Query("SELECT * FROM movies WHERE id == :movieId")
    Single<MovieExtra> getMovieById(int movieId);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovieExtra(MovieExtra movie);
        insertMovie(movie.getMovie());
        //Genre
        for (Genre genre : movie.getGenres()) {
            insertGenre(genre);
        }
        //Trailer
        for (Trailer trailer : movie.getTrailers()) {
            insertTrailer(trailer);
        }
        //Cast
        for (Cast cast : movie.getCast()) {
            insertCast(cast);
        }
    }

    @Transaction
    @Delete
    public void deleteMovieExtra(MovieExtra movie) {
        deleteMovie(movie.getMovie());
        //Genre
        for (Genre genre : movie.getGenres()) {
            deleteGenre(genre);
        }
        //Trailer
        for (Trailer trailer : movie.getTrailers()) {
            deleteTrailer(trailer);
        }
        //Cast
        for (Cast cast : movie.getCast()) {
            deleteCast(cast);
        }
    }

    @Insert
    public abstract void insertMovie(Movie movie);

    @Delete
    public abstract void deleteMovie(Movie movie);

    @Insert
    public abstract void insertCast(Cast cast);

    @Delete
    public abstract void deleteCast(Cast cast);

    @Insert
    public abstract void insertGenre(Genre genre);

    @Delete
    public abstract void deleteGenre(Genre genre);

    @Insert
    public abstract void insertTrailer(Trailer trailer);

    @Delete
    public abstract void deleteTrailer(Trailer trailer);
}

