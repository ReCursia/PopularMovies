package com.example.popularmovies.presenters;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.network.MoviesApi;
import com.example.popularmovies.pojo.DiscoverMovies;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.views.MoviesContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MoviesFragmentPresenter extends MvpPresenter<MoviesContract> {
    private MoviesApi client;
    private int currentPage;
    private String sortBy;

    //TODO inject sorting
    public MoviesFragmentPresenter(MoviesApi client, String sortBy) {
        this.client = client;
        this.sortBy = sortBy;
        currentPage = 1;
        loadMovies();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadMovies() {
        client.discoverMovies(sortBy, currentPage, NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse); //Reflection
    }

    private void handleSuccessfulResponse(DiscoverMovies discoverMovies) {
        List<Movie> movies = discoverMovies.getMovies();
        getViewState().addMovies(movies);
        currentPage++;
    }

    private void handleErrorResponse(Throwable t) {
        //getViewState().hideLoading();
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    public void bottomIsReached() {
        loadMovies();
    }

    public void onMovieClicked(Movie movie) {
        getViewState().openMovieDetailInformation(movie);
    }

}
