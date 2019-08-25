package com.recursia.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.recursia.popularmovies.models.pojo.SectionItem;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IntroContract extends MvpView {

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
