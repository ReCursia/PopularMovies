package com.example.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.popularmovies.models.pojo.Movie;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainContract extends MvpView {

    //Dialog
    void openAboutDialog();

    void hideAboutDialog();

    @StateStrategyType(SkipStrategy.class)
    void openGooglePlayPage();

    @StateStrategyType(SkipStrategy.class)
    void openDetailScreen(Movie movie);

    @StateStrategyType(SkipStrategy.class)
    void openFavoriteScreen();

}
