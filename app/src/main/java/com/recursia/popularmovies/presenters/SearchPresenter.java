package com.recursia.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.models.network.MoviesApi;
import com.recursia.popularmovies.models.pojo.DiscoverMovies;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.views.SearchContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SearchPresenter extends MvpPresenter<SearchContract> {
    private static final int TIMEOUT = 500; //ms
    private static final int QUERY_PAGE = 1;
    private final CompositeDisposable compositeDisposable;
    private MoviesApi client;

    public SearchPresenter(MoviesApi client) {
        this.client = client;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void handleErrorMessage(Throwable throwable) {
        getViewState().showErrorMessage(throwable.getLocalizedMessage());
    }

    private void handleSearchResults(DiscoverMovies discoverMovies) {
        getViewState().setMovies(discoverMovies.getMovies());
    }

    public void onItemClicked(Movie item) {
        getViewState().openDetailScreen(item);
    }

    public void onQueryTextChanged(String s) {
        //TODO make debounce, it's a complicated..
        Disposable d = client
                .getMoviesByQuery(s, QUERY_PAGE, NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSearchResults, this::handleErrorMessage);
        compositeDisposable.add(d);
    }

}
