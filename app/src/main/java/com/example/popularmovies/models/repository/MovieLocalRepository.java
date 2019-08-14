package com.example.popularmovies.models.repository;

import com.example.popularmovies.models.database.GenreDao;
import com.example.popularmovies.models.database.MovieDao;
import com.example.popularmovies.models.database.TrailerDao;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.Trailer;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieLocalRepository implements MovieRepository {
    private MovieDao movieDao;
    private TrailerDao trailerDao;
    private GenreDao genreDao;

    public MovieLocalRepository(MovieDao movieDao, TrailerDao trailerDao, GenreDao genreDao) {
        this.movieDao = movieDao;
        this.trailerDao = trailerDao;
        this.genreDao = genreDao;
    }

    @Override
    public void saveTrailers(List<Trailer> trailers, int movieId) {
        //binding movie Id so we can in further retrieve that information
        bindTrailersMovieId(trailers, movieId);
        Completable.fromAction(() -> trailerDao.insertTrailers(trailers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void bindTrailersMovieId(List<Trailer> trailers, int movieId) {
        for (Trailer trailer : trailers) {
            trailer.setMovieId(movieId);
        }
    }

    @Override
    public void saveGenres(List<Genre> genres, int movieId) {
        //binding movie Id so we can in further retrieve that information
        bindGenresMovieId(genres, movieId);
        Completable.fromAction(() -> genreDao.insertGenres(genres))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void bindGenresMovieId(List<Genre> genres, int movieId) {
        for (Genre genre : genres) {
            genre.setMovieId(movieId);
        }
    }

    @Override
    public void deleteTrailers(List<Trailer> trailers) {
        Completable.fromAction(() -> trailerDao.deleteTrailers(trailers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void deleteGenres(List<Genre> genres) {
        Completable.fromAction(() -> genreDao.deleteGenres(genres))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void saveMovie(Movie movie) {
        Completable.fromAction(() -> movieDao.insertMovie(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void deleteMovie(Movie movie) {
        Completable.fromAction(() -> movieDao.deleteMovie(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public Single<Movie> getMovieById(int movieId) {
        return movieDao.getMovieById(movieId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Trailer>> getTrailersById(int movieId) {
        return trailerDao.getTrailersById(movieId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Genre>> getGenresById(int movieId) {
        return genreDao.getGenresById(movieId)
                .subscribeOn(Schedulers.io());
    }

}
