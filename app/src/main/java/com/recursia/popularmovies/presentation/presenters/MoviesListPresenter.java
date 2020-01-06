package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.Screens;
import com.recursia.popularmovies.domain.MoviesListInteractor;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.views.contracts.MoviesListContract;
import com.recursia.popularmovies.utils.LangUtils;
import com.recursia.popularmovies.utils.discover.DiscoverStrategy;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MoviesListPresenter extends MvpPresenter<MoviesListContract> {
    private final MoviesListInteractor moviesListInteractor;
    private final DiscoverStrategy discoverStrategy;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Router router;
    private int currentPage;
    private boolean isRefreshing;
    private boolean isLoadingMore;

    public MoviesListPresenter(MoviesListInteractor moviesListInteractor, DiscoverStrategy discoverStrategy, Router router) {
        this.moviesListInteractor = moviesListInteractor;
        this.discoverStrategy = discoverStrategy;
        this.router = router;
        this.currentPage = 1;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadMovies();
    }

    private void loadMovies() {
        Disposable d = moviesListInteractor
                .discoverMovies(
                        discoverStrategy.getSortBy(),
                        currentPage,
                        discoverStrategy.getVoteCount(),
                        LangUtils.getDefaultLanguage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleMovies, this::handleError);
        compositeDisposable.add(d);
    }

    private void handleMovies(List<Movie> movies) {
        if (isRefreshing) {
            getViewState().setMovies(movies);
        } else {
            getViewState().addMovies(movies);
        }
        getViewState().hideLoading();
        isRefreshing = false;
        isLoadingMore = false;
        currentPage++;
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

    private void handleError(Throwable t) {
        getViewState().hideLoading();
        isRefreshing = false;
        isLoadingMore = false;
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    public void bottomIsReached() {
        if (!isLoadingMore) {
            isLoadingMore = true;
            loadMovies();
        }
    }

    public void onMovieClicked(Movie movie) {
        router.navigateTo(new Screens.DetailScreen(movie.getId()));
    }

}
