package com.recursia.popularmovies.presentation.views.contracts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.domain.models.Trailer;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DetailScreenContract extends MvpView {

    //Movie detail
    void setMovieDetail(Movie movie);

    void hideMovieDetail();

    void showMovieDetail();

    //Favorite icon
    void setFavoriteIconOn();

    void setFavoriteIconOff();

    void showFavoriteIcon();

    void hideFavoriteIcon();

    //Share
    @StateStrategyType(SkipStrategy.class)
    void shareMovie(Movie movie);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void showMovieAddedMessage();

    @StateStrategyType(SkipStrategy.class)
    void showMovieRemovedMessage();

    @StateStrategyType(SkipStrategy.class)
    void openTrailerUrl(Trailer trailer);

    //Movie recommendations
    void setRecommendationMovies(List<Movie> movies);

    void hideRecommendationMovies();

    void showRecommendationMovies();

}
