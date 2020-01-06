package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.domain.DetailScreenInteractor;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.domain.models.Trailer;
import com.recursia.popularmovies.presentation.views.DetailScreenContract;
import com.recursia.popularmovies.utils.LangUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class DetailScreenPresenter extends MvpPresenter<DetailScreenContract> {
    private static final int MOVIE_RECOMMENDATION_PAGE = 1;
    private final DetailScreenInteractor detailScreenInteractor;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final int movieId;

    public DetailScreenPresenter(DetailScreenInteractor detailScreenInteractor,
                                 int movieId) {
        this.detailScreenInteractor = detailScreenInteractor;
        this.movieId = movieId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().hideFavoriteIcon();
        getViewState().hideMovieDetail();
        getViewState().hideRecommendationMovies();
        initData();
    }

    private void initData() {
        loadMovie();
        loadMovieRecommendations();
    }

    private void loadMovie() {
        Disposable d = detailScreenInteractor
                .getMovieById(movieId, LangUtils.getDefaultLanguage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::handleMovie,
                        (t) -> getViewState().showErrorMessage(t.getLocalizedMessage())
                );
        compositeDisposable.add(d);
    }

    private void loadMovieRecommendations() {
        Disposable d = detailScreenInteractor
                .getMovieRecommendations(movieId, MOVIE_RECOMMENDATION_PAGE, LangUtils.getDefaultLanguage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((r) -> {
                            getViewState().setRecommendationMovies(r);
                            getViewState().showRecommendationMovies();
                        },
                        (t) -> getViewState().showErrorMessage(t.getLocalizedMessage())
                );
        compositeDisposable.add(d);
    }

    private void handleMovie(Movie movie) {
        getViewState().setMovieDetail(movie);
        getViewState().showMovieDetail();
        if (movie.isFavorite()) {
            getViewState().setFavoriteIconOn();
        } else {
            getViewState().setFavoriteIconOff();
        }
        getViewState().showFavoriteIcon();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose(); //our activity is destroyed, disposing all subscriptions
    }

    public void onFavoriteIconClicked(Movie movie) {
        if (movie.isFavorite()) {
            removeMovieFavorite(movie);
        } else {
            makeMovieFavorite(movie);
        }

    }

    private void removeMovieFavorite(Movie movie) {
        Disposable d = detailScreenInteractor
                .removeFavoriteMovie(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            getViewState().setFavoriteIconOff();
                            getViewState().showMovieRemovedMessage();
                        }
                );
        compositeDisposable.add(d);
    }

    private void makeMovieFavorite(Movie movie) {
        Disposable d = detailScreenInteractor
                .makeFavoriteMovie(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            getViewState().setFavoriteIconOn();
                            getViewState().showMovieAddedMessage();
                        }
                );
        compositeDisposable.add(d);
    }

    public void onShareIconClicked(Movie movie) {
        getViewState().shareMovie(movie);
    }

    public void onMovieClicked(Movie movie) {
        getViewState().openDetailScreen(movie);
    }

    public void onBackdropImageClicked(Movie movie) {
        getViewState().openPhotoDetail(movie.getBackdropPath());
    }

    public void onTrailerPlayButtonClicked(Trailer trailer) {
        getViewState().openTrailerUrl(trailer);
    }

}
