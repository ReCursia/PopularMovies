package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.domain.SearchScreenInteractor;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.views.SearchScreenContract;
import com.recursia.popularmovies.utils.LangUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class SearchScreenPresenter extends MvpPresenter<SearchScreenContract> {
    private static final int TIMEOUT = 500; //ms
    private static final int QUERY_PAGE = 1;
    private final SearchScreenInteractor searchScreenInteractor;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchScreenPresenter(SearchScreenInteractor searchScreenInteractor) {
        this.searchScreenInteractor = searchScreenInteractor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void handleErrorMessage(Throwable throwable) {
        getViewState().showErrorMessage(throwable.getLocalizedMessage());
    }

    public void onItemClicked(Movie item) {
        getViewState().openDetailScreen(item);
    }

    public void onQueryTextChanged(String s) {
        //TODO make debounce, it's a complicated..
        Disposable d = searchScreenInteractor
                .getMoviesByQuery(s, QUERY_PAGE, LangUtils.getDefaultLanguage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSearchResults, this::handleErrorMessage);
        compositeDisposable.add(d);
    }

    private void handleSearchResults(List<Movie> movies) {
        getViewState().setMovies(movies);
    }

}
