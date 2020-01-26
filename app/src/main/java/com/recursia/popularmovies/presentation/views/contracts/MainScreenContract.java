package com.recursia.popularmovies.presentation.views.contracts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainScreenContract extends MvpView {

    void openAboutDialog();

    void hideAboutDialog();

    @StateStrategyType(SkipStrategy.class)
    void openGooglePlayPage();

}