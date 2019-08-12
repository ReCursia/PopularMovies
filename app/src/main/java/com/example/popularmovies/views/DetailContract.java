package com.example.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.Trailer;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DetailContract extends MvpView {

    void setMovieDetail(Movie movie);

    void setFavoriteIconOn();

    void setFavoriteIconOff();

    //Trailers
    void setTrailers(List<Trailer> trailers);

    void hideTrailers();

    void showTrailers();

    //Genres
    void setGenres(List<Genre> genres);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void showMovieAddedMessage();

    @StateStrategyType(SkipStrategy.class)
    void showMovieRemovedMessage();

    @StateStrategyType(SkipStrategy.class)
    void openTrailerUrl(Trailer trailer);

}
