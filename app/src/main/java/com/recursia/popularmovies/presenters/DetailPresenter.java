package com.recursia.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.models.database.MovieDao;
import com.recursia.popularmovies.models.network.MoviesApi;
import com.recursia.popularmovies.models.pojo.Cast;
import com.recursia.popularmovies.models.pojo.Credits;
import com.recursia.popularmovies.models.pojo.DiscoverMovies;
import com.recursia.popularmovies.models.pojo.Genre;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.models.pojo.MovieExtra;
import com.recursia.popularmovies.models.pojo.MovieTrailers;
import com.recursia.popularmovies.models.pojo.Trailer;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.views.DetailContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private static final int MOVIE_RECOMMENDATION_PAGE = 1;
    private MoviesApi client;
    private MovieDao movieDao;
    private MovieExtra movieExtra;
    private boolean isFavorite;
    private CompositeDisposable compositeDisposable;
    private int movieId;

    public DetailPresenter(MoviesApi client, MovieDao movieDao, MovieExtra movieExtra, int movieId) {
        this.client = client;
        this.movieDao = movieDao;
        this.movieId = movieId;
        this.movieExtra = movieExtra;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().hideFavoriteIcon();
        getViewState().hideTrailers();
        getViewState().hideCast();
        getViewState().hideMovieDetail();
        getViewState().hideMovies();
        setDefaultFavoriteIcon();
    }

    private void setDefaultFavoriteIcon() {
        Disposable d = movieDao.getMovieById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLocalMovie, this::handleErrorLocalMovie);
        compositeDisposable.add(d);
    }

    private void handleErrorLocalMovie(Throwable throwable) {
        isFavorite = false;
        getViewState().setFavoriteIconOff();
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
                .subscribe(this::handleRemoteMovie, this::handleErrorMessage);
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

    private void handleRemoteMovie(Movie movie) {
        setMovieData(movie);
        setGenresData(movie.getGenres());
    }

    private void setMovieData(Movie movie) {
        getViewState().showFavoriteIcon();
        movieExtra.setMovie(movie);
        getViewState().setMovieDetail(movie);
        getViewState().showMovieDetail();
    }

    private void setGenresData(List<Genre> genres) {
        movieExtra.setGenres(genres);
        getViewState().setGenres(genres);
    }

    private void handleLocalMovie(MovieExtra movie) {
        isFavorite = true;
        getViewState().setFavoriteIconOn();
        setMovieData(movie.getMovie());
        setCastData(movie.getCast());
        setGenresData(movie.getGenres());
        setTrailersData(movie.getTrailers());
        //try to load recommendations
        loadMovieRecommendationsNetwork();
    }

    private void setCastData(List<Cast> cast) {
        movieExtra.setCast(cast);
        if (!cast.isEmpty()) {
            getViewState().setCast(cast);
            getViewState().showCast();
        }
    }

    private void setTrailersData(List<Trailer> trailers) {
        movieExtra.setTrailers(trailers);
        if (!trailers.isEmpty()) {
            getViewState().setTrailers(trailers);
            getViewState().showTrailers();
        }
    }

    private void setTrailersData(MovieTrailers trailers) {
        setTrailersData(trailers.getTrailers());
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

    private void setCastData(Credits credits) {
        setCastData(credits.getCast());
    }

    private void handleErrorMessage(Throwable throwable) {
        getViewState().showErrorMessage(throwable.getLocalizedMessage());
    }

    public void onFavoriteIconClicked() {
        Disposable d;
        if (isFavorite) {
            d = Completable.fromAction(() -> movieDao.deleteMovieExtra(movieExtra))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            getViewState().setFavoriteIconOff();
            getViewState().showMovieRemovedMessage();
        } else {
            d = Completable.fromAction(() -> movieDao.insertMovieExtra(movieExtra))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            getViewState().setFavoriteIconOn();
            getViewState().showMovieAddedMessage();
        }
        compositeDisposable.add(d);
        isFavorite = !isFavorite;
    }

    public void onShareIconClicked() {
        if (dataIsValid()) {
            getViewState().shareMovie(movieExtra.getMovie());
        }
    }

    private boolean dataIsValid() {
        return movieExtra.getMovie() != null;
    }

    public void onMovieClicked(Movie movie) {
        getViewState().openDetailScreen(movie);
    }

    public void onBackdropImageClicked() {
        if (dataIsValid()) {
            getViewState().openPhotoDetail(movieExtra.getMovie().getBackdropPath());
        }
    }

}
