package com.example.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IntroContract extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void openMainScreen();

    void setNextSection();

    void setPreviousSection();

    //Next button
    void showNextButton();

    void hideNextButton();

    //Prev button
    void showPreviousButton();

    void hidePreviousButton();

    //Finish button
    void showFinishButton();

    void hideFinishButton();
}
