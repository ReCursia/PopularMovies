package com.recursia.popularmovies.models.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.recursia.popularmovies.models.pojo.Cast;
import com.recursia.popularmovies.models.pojo.Genre;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.models.pojo.MovieExtra;
import com.recursia.popularmovies.models.pojo.Trailer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public abstract class MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    public abstract Flowable<List<MovieExtra>> getAllMovies();

    @Transaction
    @Query("SELECT * FROM movies WHERE id == :movieId")
    public abstract Single<MovieExtra> getMovieById(int movieId);

    @Transaction
    public void insertMovieExtra(MovieExtra movie) {
        insertMovie(movie.getMovie());
        //Genre
        for (Genre genre : movie.getGenres()) {
            genre.setMovieId(movie.getMovie().getId());
            insertGenre(genre);
        }
        //Trailer
        for (Trailer trailer : movie.getTrailers()) {
            trailer.setMovieId(movie.getMovie().getId());
            insertTrailer(trailer);
        }
        //Cast
        for (Cast cast : movie.getCast()) {
            cast.setMovieId(movie.getMovie().getId());
            insertCast(cast);
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertGenre(Genre genre);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertTrailer(Trailer trailer);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertCast(Cast cast);

    public void deleteMovieExtra(MovieExtra movie) {
        deleteMovie(movie.getMovie());
    }

    @Delete
    abstract void deleteMovie(Movie movie);

}

