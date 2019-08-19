package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.views.FavoriteContract;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteContract> {
    private CompositeDisposable compositeDisposable;

    public FavoritePresenter() {
        this.compositeDisposable = new CompositeDisposable();
        getViewState().hideNoFavoriteScreen();
        initMovies();
    }

    private void initMovies() {
        //TODO implement database favorite movies call
        //TODO dont forget about DISPOSABLE
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void handleResult(List<Movie> movies) {
        if (!movies.isEmpty()) {
            getViewState().setMovies(movies);
            getViewState().hideNoFavoriteScreen();
        } else {
            getViewState().showNoFavoriteScreen();
        }
    }

    private void handleError(Throwable t) {
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    public void onItemClicked(Movie movie) {
        getViewState().openDetailScreen(movie);
    }

}
