package com.recursia.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.models.network.MoviesApi;
import com.recursia.popularmovies.models.pojo.DiscoverMovies;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.views.SearchContract;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

@InjectViewState
public class SearchPresenter extends MvpPresenter<SearchContract> {
    private static final int TIMEOUT = 500; //ms
    private static final int QUERY_PAGE = 1;
    private final CompositeDisposable compositeDisposable;
    private MoviesApi client;
    private PublishSubject<String> publishSubject;

    public SearchPresenter(MoviesApi client) {
        this.client = client;
        this.publishSubject = PublishSubject.create();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initPublishSubject();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initPublishSubject() {
        Disposable d = publishSubject
                .debounce(TIMEOUT, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap((Function<String, ObservableSource<DiscoverMovies>>)
                        s -> client.getMovieByQuery(s, QUERY_PAGE, NetworkUtils.getDefaultLanguage()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSearchResults, this::handleErrorMessage);
        compositeDisposable.add(d);
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
        publishSubject.onNext(s);
    }
}
