package com.recursia.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.models.network.MoviesApi;
import com.recursia.popularmovies.models.pojo.DiscoverMovies;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.utils.discover.DiscoverStrategy;
import com.recursia.popularmovies.views.MoviesContract;

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
    private boolean isLoading;

    public MoviesFragmentPresenter(MoviesApi client, DiscoverStrategy discoverStrategy) {
        this.client = client;
        this.discoverStrategy = discoverStrategy;
        this.compositeDisposable = new CompositeDisposable();
        currentPage = 1;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
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
        isLoading = true;
        loadMovies();
    }

    private void handleSuccessfulResponse(DiscoverMovies discoverMovies) {
        List<Movie> movies = discoverMovies.getMovies();
        if (isLoading) {
            getViewState().setMovies(movies);
        } else {
            getViewState().addMovies(movies);
        }
        getViewState().hideLoading();
        isLoading = false;
        currentPage++;
    }

    private void handleErrorResponse(Throwable t) {
        getViewState().hideLoading();
        isLoading = false;
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    public void bottomIsReached() {
        if (!isLoading) {
            onSwipeRefreshed();
        }
    }

    public void onMovieClicked(Movie movie) {
        getViewState().openMovieDetailInformation(movie);
    }

}
