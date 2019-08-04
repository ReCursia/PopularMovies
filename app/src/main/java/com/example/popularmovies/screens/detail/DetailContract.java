package com.example.popularmovies.screens.detail;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.popularmovies.pojo.Movie;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DetailContract extends MvpView {
    void setMovieDetail(Movie title);

    void setFavoriteIconOn();

    void setFavoriteIconOff();

    @StateStrategyType(SkipStrategy.class)
    void showMovieAddedMessage();

    @StateStrategyType(SkipStrategy.class)
    void showMovieRemovedMessage();
}
