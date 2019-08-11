package com.example.popularmovies.presenters;

import android.annotation.SuppressLint;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.database.GenreDao;
import com.example.popularmovies.models.database.MovieDao;
import com.example.popularmovies.models.database.TrailerDao;
import com.example.popularmovies.models.network.MoviesApi;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Genres;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.MovieTrailers;
import com.example.popularmovies.models.pojo.Trailer;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.views.DetailContract;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private MoviesApi client;
    private Movie movie;
    private List<Trailer> trailers;
    private List<Genre> genres;
    private MovieDao movieDao;
    private TrailerDao trailerDao;
    private GenreDao genreDao;
    private int movieId;
    private boolean isFavorite;

    public DetailPresenter(MoviesApi client, MovieDao movieDao,TrailerDao trailerDao, GenreDao genreDao, int movieId) {
        this.client = client;
        this.movieDao = movieDao;
        this.trailerDao = trailerDao;
        this.genreDao = genreDao;
        this.movieId = movieId;
        getViewState().hideTrailers();
    }

    private void handleSuccessfulResponse(MovieTrailers movieTrailers) {
        handleSuccessfulResponse(movieTrailers.getTrailers());
    }

    private void handleSuccessfulResponse(List<Trailer> trailers) {
        this.trailers = trailers;
        if (!trailers.isEmpty()) {
            getViewState().setTrailers(trailers);
            getViewState().showTrailers();
        } else {
            getViewState().hideTrailers();
        }
    }

    private void handleErrorResponse(Throwable t) {
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    public void onFavoriteIconClicked() {
        if (isFavorite) {
            deleteFavoriteMovie();
        } else {
            insertFavoriteMovie();
        }
        isFavorite = !isFavorite;
    }

    private void deleteFavoriteMovie() {
        deleteMovie();
        deleteTrailers();
        deleteGenres();

        getViewState().setFavoriteIconOff();
        getViewState().showMovieRemovedMessage();
    }

    private void deleteMovie() {
        Completable.fromAction(() -> movieDao.deleteMovie(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void deleteTrailers() {
        Completable.fromAction(() -> trailerDao.deleteTrailers(trailers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void deleteGenres() {
        Completable.fromAction(() -> genreDao.deleteGenres(genres))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void insertFavoriteMovie() {
        saveMovie();
        saveTrailers();
        saveGenres();

        getViewState().setFavoriteIconOn();
        getViewState().showMovieAddedMessage();
    }

    private void saveMovie() {
        Completable.fromAction(() -> movieDao.insertMovie(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void saveTrailers() {
        //before save, we need to save movieId for each trailer
        bindMovieIdToEachTrailer();
        Completable.fromAction(() -> trailerDao.insertTrailers(trailers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void bindMovieIdToEachTrailer() {
        for (Trailer trailer : trailers) {
            trailer.setMovieId(movieId);
        }
    }

    private void saveGenres() {
        //before save, we need to save movieId for each trailer
        bindMovieIdToEachGenre();
        Completable.fromAction(() -> genreDao.insertGenres(genres))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void bindMovieIdToEachGenre() {
        for (Genre genre : genres) {
            genre.setMovieId(movieId);
        }
    }

    public void onTrailerPlayButtonClicked(Trailer trailer) {
        getViewState().openTrailerUrl(trailer);
    }

    public void menuIsInflated() {
        setDefaultFavoriteIcon();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void setDefaultFavoriteIcon() {
        movieDao.getMovieById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((movie) -> {
                    handleSuccessfulResponse(movie);
                    getViewState().setFavoriteIconOn();
                    isFavorite = true;
                    loadTrailersFromDb();
                    loadGenresFromDb();
                }, e -> {
                    getViewState().setFavoriteIconOff();
                    //trying to load from network
                    initMovieData();
                    initTrailers();
                });
    }

    private void handleSuccessfulResponse(Movie movie) {
        this.movie = movie;
        getViewState().setMovieDetail(movie);
        initGenres();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void initGenres() {
        client.getAllGenres(NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulGenres, this::handleErrorResponse);

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadTrailersFromDb() {
        trailerDao.getTrailersById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadGenresFromDb() {
        genreDao.getGenresById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulGenres, this::handleErrorResponse);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void initMovieData() {
        client.getMovieById(movieId, NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void initTrailers() {
        client.getMovieTrailersById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse);
    }

    private void handleSuccessfulGenres(Genres genres) {
        handleSuccessfulGenres(genres.getGenres());
    }

    private void handleSuccessfulGenres(List<Genre> genres) {
        this.genres = genres;
        if (!genres.isEmpty()) {
            getViewState().setGenres(genres);
        }
    }

}
