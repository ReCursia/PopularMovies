package com.example.popularmovies.screens.detail;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.pojo.MovieTrailers;
import com.example.popularmovies.pojo.Trailer;
import com.example.popularmovies.repository.MoviesApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private MoviesApi client;
    private int movieId;

    public DetailPresenter(MoviesApi client, int movieId) {
        this.client = client;
        this.movieId = movieId;
        getViewState().hideTrailers();
        initTrailers();
        initMovieData();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void initTrailers() {
        client.getMovieTrailersById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void initMovieData() {
        client.getMovieById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse);
    }

    private void handleSuccessfulResponse(MovieTrailers movieTrailers) {
        List<Trailer> trailers = movieTrailers.getTrailers();
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

    private void handleSuccessfulResponse(Movie movie) {
        getViewState().setMovieDetail(movie);
    }

    public void onFavoriteIconClicked() {
        getViewState().setFavoriteIconOn();
        getViewState().showMovieAddedMessage();
        //TODO implement
    }

    public void onTrailerPlayButtonClicked(int position) {
        getViewState().openTrailerUrl(position);
    }

    public void menuIsInflated() {
        setDefaultFavoriteIcon();
    }

    private void setDefaultFavoriteIcon() {
        //TODO implement
        getViewState().setFavoriteIconOff();
    }

}
