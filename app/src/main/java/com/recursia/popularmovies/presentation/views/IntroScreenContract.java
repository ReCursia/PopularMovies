package com.recursia.popularmovies.presentation.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.recursia.popularmovies.presentation.models.SectionItem;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IntroScreenContract extends MvpView {

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

    //View pager
    void setViewPagerData(List<SectionItem> sectionItems);

}
