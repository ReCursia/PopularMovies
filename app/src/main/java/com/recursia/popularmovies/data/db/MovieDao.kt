package com.recursia.popularmovies.data.db

import android.arch.persistence.room.*
import com.recursia.popularmovies.data.models.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class MovieDao {

    @get:Transaction
    @get:Query("SELECT * FROM movies")
    abstract val allMovies: Flowable<List<MovieExtraDatabaseModel>>

    @Transaction
    @Query("SELECT * FROM movies WHERE id == :movieId")
    abstract fun getMovieById(movieId: Int): Single<MovieExtraDatabaseModel>

    @Transaction
    open fun insertMovieExtra(movieExtra: MovieExtraDatabaseModel) {
        val movie = movieExtra.movie
        insertMovie(movie)

        // Genre
        for (genre in movieExtra.genres) {
            genre.movieId = movie.id
            insertGenre(genre)
        }
        // Trailer
        for (trailer in movieExtra.trailers) {
            trailer.movieId = movie.id
            insertTrailer(trailer)
        }
        // Cast
        for (cast in movieExtra.cast) {
            cast.movieId = movie.id
            insertCast(cast)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    internal abstract fun insertMovie(movie: MovieDatabaseModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    internal abstract fun insertGenre(genre: GenreDatabaseModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    internal abstract fun insertTrailer(trailer: TrailerDatabaseModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    internal abstract fun insertCast(cast: CastDatabaseModel)

    open fun deleteMovieExtra(movie: MovieExtraDatabaseModel) {
        deleteMovie(movie.movie)
    }

    @Delete
    internal abstract fun deleteMovie(movie: MovieDatabaseModel)
}
