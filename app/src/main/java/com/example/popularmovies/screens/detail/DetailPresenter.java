package com.example.popularmovies.screens.detail;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.database.MovieDao;
import com.example.popularmovies.database.TrailerDao;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.pojo.MovieTrailers;
import com.example.popularmovies.pojo.Trailer;
import com.example.popularmovies.repository.MoviesApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private MoviesApi client;
    private Movie movie;
    private List<Trailer> trailers;
    private MovieDao movieDao;
    private TrailerDao trailerDao;
    private int movieId;
    private boolean isFavorite;

    public DetailPresenter(MoviesApi client, MovieDao movieDao, TrailerDao trailerDao, int movieId) {
        this.client = client;
        this.movieDao = movieDao;
        this.trailerDao = trailerDao;
        this.movieId = movieId;
        getViewState().hideTrailers();
        initTrailers(); //из интернета
        initMovieData(); //из интернета
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void initTrailers() {
        client.getMovieTrailersById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void initMovieData() {
        client.getMovieById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse);
    }

    private void handleSuccessfulResponse(MovieTrailers movieTrailers) {
        this.trailers = movieTrailers.getTrailers();
        if (!trailers.isEmpty()) {
            getViewState().setTrailers(trailers);
            getViewState().showTrailers();
        } else {
            getViewState().hideTrailers();
        }
    }

    private void handleErrorResponse(Throwable t) {
        getViewState().showErrorMessage(t.getLocalizedMessage());
    }

    private void handleSuccessfulResponse(Movie movie) {
        this.movie = movie;
        getViewState().setMovieDetail(movie);
    }

    @SuppressLint("CheckResult")
    public void onFavoriteIconClicked() {
        if (isFavorite) {
            deleteFavoriteMovie();
        } else {
            insertFavoriteMovie();
        }
        isFavorite = !isFavorite;
    }

    @SuppressLint("CheckResult")
    private void deleteFavoriteMovie() {
        //Delete movie
        Completable.fromAction(() -> movieDao.deleteMovie(movie));
        //Delete trailers
        /*
        Completable.fromAction(() -> trailerDao.deleteTrailers(trailers));
        getViewState().setFavoriteIconOff();
        getViewState().showMovieRemovedMessage();
        */
    }

    @SuppressLint("CheckResult")
    private void insertFavoriteMovie() {
        //Save movie
        Completable.fromAction(() -> movieDao.insertMovie(movie));
        //Save trailers
        /*
        bindMovieIdToEachTrailer(); //TODO think about better solution

        Completable.fromAction(() -> trailerDao.insertTrailers(trailers));
        */
        getViewState().setFavoriteIconOn();
        getViewState().showMovieAddedMessage();
    }

    private void bindMovieIdToEachTrailer() {
        for (Trailer trailer : trailers) {
            trailer.setMovieId(movie.getId());
        }
    }

    public void onTrailerPlayButtonClicked(int position) {
        getViewState().openTrailerUrl(position);
    }

    public void menuIsInflated() {
        setDefaultFavoriteIcon();
    }

    @SuppressLint("CheckResult")
    private void setDefaultFavoriteIcon() {
        movieDao.getMovieById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Movie>() {
                    @Override
                    public void onSuccess(Movie movie) {
                        if (movie != null) {
                            getViewState().setFavoriteIconOn();
                            isFavorite = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().setFavoriteIconOff();
                        getViewState().showErrorMessage(e.getLocalizedMessage());
                    }
                });
    }

}
