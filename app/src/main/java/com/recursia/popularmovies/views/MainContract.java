package com.recursia.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.recursia.popularmovies.models.pojo.Movie;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainContract extends MvpView {

    void openAboutDialog();

    void hideAboutDialog();

    @StateStrategyType(SkipStrategy.class)
    void openSearchScreen();

    @StateStrategyType(SkipStrategy.class)
    void openGooglePlayPage();

    @StateStrategyType(SkipStrategy.class)
    void openDetailScreen(Movie movie);

    @StateStrategyType(SkipStrategy.class)
    void openFavoriteScreen();

}
