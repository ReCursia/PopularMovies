package com.example.popularmovies.presenters;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.network.MoviesApi;
import com.example.popularmovies.pojo.DiscoverMovies;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.utils.discover.DiscoverStrategy;
import com.example.popularmovies.views.MoviesContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MoviesFragmentPresenter extends MvpPresenter<MoviesContract> {
    private MoviesApi client;
    private int currentPage;
    private DiscoverStrategy discoverStrategy;
    private boolean isRefreshing;

    public MoviesFragmentPresenter(MoviesApi client, DiscoverStrategy discoverStrategy) {
        this.client = client;
        this.discoverStrategy = discoverStrategy;
        currentPage = 1;
        loadMovies();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadMovies() {
        client.discoverMovies(discoverStrategy.getSortBy(), currentPage, NetworkUtils.getDefaultLanguage(), discoverStrategy.getVoteCount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse); //Reflection
    }

    public void setDiscoverStrategy(DiscoverStrategy newStrategy) {
        this.discoverStrategy = newStrategy;
    }

    public void onSwipeRefreshed() {
        getViewState().showLoading();
        isRefreshing = true;
        loadMovies();
    }

    private void handleSuccessfulResponse(DiscoverMovies discoverMovies) {
        List<Movie> movies = discoverMovies.getMovies();
        if (isRefreshing) {
            getViewState().setMovies(movies);
        } else {
            getViewState().addMovies(movies);
        }
        getViewState().hideLoading();
        isRefreshing = false;
        currentPage++;
    }

    private void handleErrorResponse(Throwable t) {
        getViewState().hideLoading();
        isRefreshing = false;
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    public void bottomIsReached() {
        loadMovies();
    }

    public void onMovieClicked(Movie movie) {
        getViewState().openMovieDetailInformation(movie);
    }

}
