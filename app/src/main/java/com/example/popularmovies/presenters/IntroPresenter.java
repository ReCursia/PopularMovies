package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.views.IntroContract;

@InjectViewState
public class IntroPresenter extends MvpPresenter<IntroContract> {
    private static final int SIZE = 3;
    private int currentSection;

    public IntroPresenter() {
        setCurrentSection(0);
    }

    private void setCurrentSection(int index) {
        currentSection = index;
        if (isInitialPosition()) {
            getViewState().hidePreviousButton();
            getViewState().showNextButton();
            getViewState().hideFinishButton();
        } else if (isInRangeExclude()) {
            getViewState().showPreviousButton();
            getViewState().showNextButton();
            getViewState().hideFinishButton();
        } else if (isEndPosition()) {
            getViewState().showPreviousButton();
            getViewState().hideNextButton();
            getViewState().showFinishButton();
        }
    }

    private boolean isInitialPosition() {
        return currentSection == 0;
    }

    private boolean isInRangeExclude() {
        return (currentSection > 0) && (currentSection < (SIZE - 1));
    }

    private boolean isEndPosition() {
        return currentSection == (SIZE - 1);
    }

    public void onNextButtonClicked() {
        getViewState().setNextSection();
    }

    public void onPreviousButtonClicked() {
        getViewState().setPreviousSection();
    }

    public void onFinishButtonClicked() {
        getViewState().openMainScreen();
    }

    public void onPageSelected(int i) {
        setCurrentSection(i);
    }

}
