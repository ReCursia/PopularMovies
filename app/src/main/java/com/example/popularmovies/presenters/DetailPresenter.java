package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.network.MoviesApi;
import com.example.popularmovies.models.pojo.Cast;
import com.example.popularmovies.models.pojo.Credits;
import com.example.popularmovies.models.pojo.DiscoverMovies;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.MovieTrailers;
import com.example.popularmovies.models.pojo.Trailer;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.views.DetailContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private static final int MOVIE_RECOMMENDATION_PAGE = 1;
    private MoviesApi client;
    private Movie movie;
    private List<Trailer> trailers;
    private List<Genre> genres;
    private List<Cast> cast;
    private boolean isFavorite;
    private CompositeDisposable compositeDisposable;
    private int movieId;

    public DetailPresenter(MoviesApi client, int movieId) {
        this.client = client;
        this.movieId = movieId;
        this.compositeDisposable = new CompositeDisposable();
        getViewState().hideTrailers();
        getViewState().hideCast();
        getViewState().hideMovieDetail();
        getViewState().hideMovies();
        setDefaultFavoriteIcon();
    }

    private void setDefaultFavoriteIcon() {
        //TODO implement database call
        loadDataFromNetwork();
    }

    private void loadDataFromNetwork() {
        loadMovieFromNetwork();
        loadTrailersFromNetwork();
        loadCastFromNetwork();
        loadMovieRecommendationsNetwork();
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

    private void loadMovieRecommendationsNetwork() {
        Disposable d = client.getMovieRecommendations(movieId, MOVIE_RECOMMENDATION_PAGE, NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMoviesRecommendationData, this::handleErrorMessage);
        compositeDisposable.add(d);
    }

    private void setTrailersData(MovieTrailers trailers) {
        setTrailersData(trailers.getTrailers());
    }

    private void setTrailersData(List<Trailer> trailers) {
        this.trailers = trailers;
        if (!trailers.isEmpty()) {
            getViewState().setTrailers(trailers);
            getViewState().showTrailers();
        }
    }

    public void onTrailerPlayButtonClicked(Trailer trailer) {
        getViewState().openTrailerUrl(trailer);
    }

    private void setMoviesRecommendationData(DiscoverMovies discoverMovies) {
        setMoviesRecommendationData(discoverMovies.getMovies());
    }

    private void setMoviesRecommendationData(List<Movie> movies) {
        if (!movies.isEmpty()) {
            getViewState().setRecommendationMovies(movies);
            getViewState().showMovies();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose(); //our activity is destroyed, disposing all subscriptions
    }

    private void setMovieData(Movie movie) {
        this.movie = movie;
        this.genres = movie.getGenres();
        getViewState().setMovieDetail(movie);
        getViewState().setGenres(genres);
        getViewState().showMovieDetail();
    }

    private void setCastData(Credits credits) {
        setCastData(credits.getCast());
    }

    private void setCastData(List<Cast> cast) {
        this.cast = cast;
        if (!cast.isEmpty()) {
            getViewState().setCast(cast);
            getViewState().showCast();
        }
    }

    private void handleErrorMessage(Throwable throwable) {
        getViewState().showErrorMessage(throwable.getLocalizedMessage());
    }

    public void onFavoriteIconClicked() {
        //TODO implement save and delete movie from database
        if (isFavorite) {
            getViewState().setFavoriteIconOff();
            getViewState().showMovieRemovedMessage();
        } else {
            getViewState().setFavoriteIconOn();
            getViewState().showMovieAddedMessage();
        }
        isFavorite = !isFavorite;
    }

    public void onShareIconClicked() {
        //TODO check if valid
        getViewState().shareMovie(movie);
    }

    public void onMovieClicked(Movie movie) {
        getViewState().openDetailScreen(movie);
    }

}
