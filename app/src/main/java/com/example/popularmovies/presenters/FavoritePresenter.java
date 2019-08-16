package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.database.MovieDao;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.views.FavoriteContract;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteContract> {
    private MovieDao movieDao;
    private CompositeDisposable compositeDisposable;

    public FavoritePresenter(MovieDao movieDao) {
        this.movieDao = movieDao;
        this.compositeDisposable = new CompositeDisposable();
        getViewState().hideNoFavoriteScreen();
        initMovies();
    }

    private void initMovies() {
        /*Disposable d = movieDao.getAllMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);
        compositeDisposable.add(d);*/
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
