package com.example.popularmovies.presenters;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.network.MoviesApi;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Genres;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.MovieTrailers;
import com.example.popularmovies.models.pojo.Trailer;
import com.example.popularmovies.models.repository.MovieRepository;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.views.DetailContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private MoviesApi client;
    private Movie movie;
    private List<Trailer> trailers;
    private List<Genre> genres;
    private boolean isFavorite;
    private MovieRepository repository;
    private int movieId;

    public DetailPresenter(MoviesApi client, MovieRepository repository, int movieId) {
        this.client = client;
        this.repository = repository;
        this.movieId = movieId;
        getViewState().hideTrailers();
    }

    private void setTrailersData(MovieTrailers trailers) {
        setTrailersData(trailers.getTrailers());
    }

    private void setTrailersData(List<Trailer> trailers) {
        this.trailers = trailers;
        getViewState().setTrailers(trailers);
        getViewState().showTrailers();
    }

    public void onTrailerPlayButtonClicked(Trailer trailer) {
        getViewState().openTrailerUrl(trailer);
    }

    public void menuIsInflated() {
        setDefaultFavoriteIcon();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void setDefaultFavoriteIcon() {
        repository.getMovieById(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    getViewState().setFavoriteIconOn();
                    this.isFavorite = true;
                    setMovieData(movie);
                    loadDataFromDb();
                }, throwable -> {
                    getViewState().setFavoriteIconOff();
                    loadDataFromNetwork();
                });
    }

    private void setMovieData(Movie movie) {
        this.movie = movie;
        getViewState().setMovieDetail(movie);
    }

    private void loadDataFromDb() {
        loadTrailersFromDb();
        loadGenresFromDb();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadTrailersFromDb() {
        repository.getTrailersById(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setTrailersData, this::handleErrorMessage);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadGenresFromDb() {
        repository.getGenresById(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setGenresData, this::handleErrorMessage);
    }

    private void loadDataFromNetwork() {
        loadMovieFromNetwork();
        loadTrailersFromNetwork();
        loadGenresFromNetwork();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadMovieFromNetwork() {
        client.getMovieById(movieId, NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMovieData, this::handleErrorMessage);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadTrailersFromNetwork() {
        client.getMovieTrailersById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setTrailersData, this::handleErrorMessage);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadGenresFromNetwork() {
        client.getAllGenres(NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setGenresData, this::handleErrorMessage);
    }

    private void handleErrorMessage(Throwable throwable) {
        getViewState().showErrorMessage(throwable.getLocalizedMessage());
    }

    private void setGenresData(Genres genres) {
        setGenresData(genres.getGenres());
    }

    private void setGenresData(List<Genre> genres) {
        this.genres = genres;
        getViewState().setGenres(genres);
    }

    public void onFavoriteIconClicked() {
        //TODO make valid checking
        if (isFavorite) {
            repository.deleteMovie(movie);
            repository.deleteTrailers(trailers);
            repository.deleteGenres(genres);
            getViewState().setFavoriteIconOff();
            getViewState().showMovieRemovedMessage();
        } else {
            repository.saveMovie(movie);
            repository.saveTrailers(trailers, movieId);
            repository.saveGenres(genres, movieId);
            getViewState().setFavoriteIconOn();
            getViewState().showMovieAddedMessage();
        }
        isFavorite = !isFavorite;
    }

}
