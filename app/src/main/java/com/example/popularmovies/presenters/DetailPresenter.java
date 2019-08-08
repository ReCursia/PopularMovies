package com.example.popularmovies.presenters;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.database.MovieDao;
import com.example.popularmovies.database.TrailerDao;
import com.example.popularmovies.network.MoviesApi;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.pojo.MovieTrailers;
import com.example.popularmovies.pojo.Trailer;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.views.DetailContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private MoviesApi client;
    private Movie movie;
    private List<Trailer> trailers;
    private MovieDao movieDao;
    private TrailerDao trailerDao;
    private int movieId;
    private boolean isFavorite;

    public DetailPresenter(MoviesApi client, MovieDao movieDao, TrailerDao trailerDao, int movieId) {
        this.client = client;
        this.movieDao = movieDao;
        this.trailerDao = trailerDao;
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

    @SuppressLint("CheckResult")
    private void deleteFavoriteMovie() {
        //Delete movie
        deleteMovie();
        //Delete trailers
        deleteTrailers();

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

    @SuppressLint("CheckResult")
    private void insertFavoriteMovie() {
        saveMovie();
        saveTrailers();

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

}
