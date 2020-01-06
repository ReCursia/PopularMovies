package com.recursia.popularmovies.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.recursia.popularmovies.data.models.CastDatabaseModel;
import com.recursia.popularmovies.data.models.GenreDatabaseModel;
import com.recursia.popularmovies.data.models.MovieDatabaseModel;
import com.recursia.popularmovies.data.models.MovieExtraDatabaseModel;
import com.recursia.popularmovies.data.models.TrailerDatabaseModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public abstract class MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    public abstract Flowable<List<MovieExtraDatabaseModel>> getAllMovies();

    @Transaction
    @Query("SELECT * FROM movies WHERE id == :movieId")
    public abstract Single<MovieExtraDatabaseModel> getMovieById(int movieId);

    @Transaction
    public void insertMovieExtra(MovieExtraDatabaseModel movieExtra) {
        MovieDatabaseModel movie = movieExtra.getMovie();
        insertMovie(movie);

        //Genre
        for (GenreDatabaseModel genre : movieExtra.getGenres()) {
            genre.setMovieId(movie.getId());
            insertGenre(genre);
        }
        //Trailer
        for (TrailerDatabaseModel trailer : movieExtra.getTrailers()) {
            trailer.setMovieId(movie.getId());
            insertTrailer(trailer);
        }
        //Cast
        for (CastDatabaseModel cast : movieExtra.getCast()) {
            cast.setMovieId(movie.getId());
            insertCast(cast);
        }
    }

    public void deleteMovieExtra(MovieExtraDatabaseModel movie) {
        deleteMovie(movie.getMovie());
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertMovie(MovieDatabaseModel movie);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertGenre(GenreDatabaseModel genre);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertTrailer(TrailerDatabaseModel trailer);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertCast(CastDatabaseModel cast);

    @Delete
    abstract void deleteMovie(MovieDatabaseModel movie);

}

