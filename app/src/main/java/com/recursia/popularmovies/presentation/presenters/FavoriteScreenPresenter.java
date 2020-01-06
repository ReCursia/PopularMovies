package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.Screens;
import com.recursia.popularmovies.domain.FavoriteScreenInteractor;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.views.contracts.FavoriteScreenContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class FavoriteScreenPresenter extends MvpPresenter<FavoriteScreenContract> {
    private final FavoriteScreenInteractor favoriteScreenInteractor;
    private final Router router;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FavoriteScreenPresenter(FavoriteScreenInteractor favoriteScreenInteractor, Router router) {
        this.favoriteScreenInteractor = favoriteScreenInteractor;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().hideNoFavoriteScreen();
        initMovies();
    }

    private void initMovies() {
        Disposable d = favoriteScreenInteractor.getAllFavoriteMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);
        compositeDisposable.add(d);
    }

    private void handleResult(List<Movie> movies) {
        if (!movies.isEmpty()) {
            getViewState().setMovies(movies);
            getViewState().hideNoFavoriteScreen();
        } else {
            getViewState().showNoFavoriteScreen();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void handleError(Throwable t) {
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    public void onItemClicked(Movie movie) {
        router.navigateTo(new Screens.DetailScreen(movie.getId()));
    }

    public void onBackPressed() {
        router.exit();
    }

}
