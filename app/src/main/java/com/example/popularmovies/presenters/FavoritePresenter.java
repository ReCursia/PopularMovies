package com.example.popularmovies.presenters;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.database.MovieDao;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.views.FavoriteContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteContract> {
    private MovieDao movieDao;

    public FavoritePresenter(MovieDao movieDao) {
        this.movieDao = movieDao;
        initMovies();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void initMovies() {
        movieDao.getAllMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);
    }

    private void handleResult(List<Movie> movies) {
        getViewState().setMovies(movies);
    }

    private void handleError(Throwable t) {
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    public void onItemClicked(Movie movie) {
        getViewState().openDetailScreen(movie);
    }

}
