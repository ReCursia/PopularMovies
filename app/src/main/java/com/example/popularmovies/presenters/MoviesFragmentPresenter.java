package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.network.MoviesApi;
import com.example.popularmovies.models.pojo.DiscoverMovies;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.utils.discover.DiscoverStrategy;
import com.example.popularmovies.views.MoviesContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MoviesFragmentPresenter extends MvpPresenter<MoviesContract> {
    private MoviesApi client;
    private int currentPage;
    private DiscoverStrategy discoverStrategy;
    private CompositeDisposable compositeDisposable;
    private boolean isRefreshing;

    public MoviesFragmentPresenter(MoviesApi client, DiscoverStrategy discoverStrategy) {
        this.client = client;
        this.discoverStrategy = discoverStrategy;
        this.compositeDisposable = new CompositeDisposable();
        currentPage = 1;
        loadMovies();
    }

    private void loadMovies() {
        Disposable d = client.discoverMovies(discoverStrategy.getSortBy(), currentPage, discoverStrategy.getVoteCount(), NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse);
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
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
