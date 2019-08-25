package com.recursia.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.models.database.MovieDao;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.models.pojo.MovieExtra;
import com.recursia.popularmovies.views.FavoriteContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteContract> {
    private CompositeDisposable compositeDisposable;
    private MovieDao movieDao;

    public FavoritePresenter(MovieDao movieDao) {
        this.movieDao = movieDao;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().hideNoFavoriteScreen();
        initMovies();
    }

    private void initMovies() {
        Disposable d = movieDao.getAllMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);
        compositeDisposable.add(d);
    }

    private void handleResult(List<MovieExtra> moviesExtra) {
        List<Movie> movies = getAllMovies(moviesExtra);

        if (!movies.isEmpty()) {
            getViewState().setMovies(movies);
            getViewState().hideNoFavoriteScreen();
        } else {
            getViewState().showNoFavoriteScreen();
        }
    }

    private List<Movie> getAllMovies(List<MovieExtra> moviesExtra) {
        List<Movie> movies = new ArrayList<>();
        for (MovieExtra movieExtra : moviesExtra) {
            movies.add(movieExtra.getMovie());
        }
        return movies;
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
        getViewState().openDetailScreen(movie);
    }

}
