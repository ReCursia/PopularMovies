package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.network.MoviesApi;
import com.example.popularmovies.models.pojo.Cast;
import com.example.popularmovies.models.pojo.Credits;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.MovieTrailers;
import com.example.popularmovies.models.pojo.Trailer;
import com.example.popularmovies.models.repository.MovieRepository;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.views.DetailContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private MoviesApi client;
    private Movie movie;
    private List<Trailer> trailers;
    private List<Genre> genres;
    private List<Cast> cast;
    private boolean isFavorite;
    private MovieRepository repository;
    private CompositeDisposable compositeDisposable;
    private int movieId;

    public DetailPresenter(MoviesApi client, MovieRepository repository, int movieId) {
        this.client = client;
        this.repository = repository;
        this.movieId = movieId;
        this.compositeDisposable = new CompositeDisposable();
        getViewState().hideTrailers();
        getViewState().hideCast();
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

    private void setDefaultFavoriteIcon() {
        /*
        Disposable d = repository.getMovieById(movieId)
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
        compositeDisposable.add(d);
        */
        loadDataFromNetwork();
    }

    private void loadDataFromNetwork() {
        loadMovieFromNetwork();
        loadTrailersFromNetwork();
        loadCastFromNetwork();
    }

    private void loadMovieFromNetwork() {
        Disposable d = client.getMovieById(movieId, NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMovieData, this::handleErrorMessage);
        compositeDisposable.add(d);
    }

    private void loadTrailersFromNetwork() {
        Disposable d = client.getMovieTrailersById(movieId, NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setTrailersData, this::handleErrorMessage);
        compositeDisposable.add(d);
    }

    private void loadCastFromNetwork() {
        Disposable d = client.getMovieCreditsById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setCastData, this::handleErrorMessage);
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose(); //our activity is destroyed, disposing all subscriptions
    }

    private void setMovieData(Movie movie) {
        this.movie = movie;
        getViewState().setMovieDetail(movie);
        getViewState().setGenres(movie.getGenres());
    }

    private void loadDataFromDb() {
        loadTrailersFromDb();
        loadGenresFromDb();
    }

    private void loadTrailersFromDb() {
        Disposable d = repository.getTrailersById(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setTrailersData, this::handleErrorMessage);
        compositeDisposable.add(d);
    }

    private void loadGenresFromDb() {
        Disposable d = repository.getGenresById(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setGenresData, this::handleErrorMessage);
        compositeDisposable.add(d);
    }

    private void setCastData(Credits credits) {
        setCastData(credits.getCast());
    }

    private void setCastData(List<Cast> cast) {
        this.cast = cast;
        getViewState().setCast(cast);
        getViewState().showCast();
    }

    private void handleErrorMessage(Throwable throwable) {
        getViewState().showErrorMessage(throwable.getLocalizedMessage());
    }

    private void setGenresData(List<Genre> genres) {
        this.genres = genres;
        getViewState().setGenres(genres);
    }

    public void onFavoriteIconClicked() {
        //TODO make valid checking
        /*
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
        */
    }

}
