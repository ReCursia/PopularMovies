package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.utils.intro.PrefUtils;
import com.example.popularmovies.views.IntroContract;

@InjectViewState
public class IntroPresenter extends MvpPresenter<IntroContract> {
    private static final int SIZE = 3;
    private int currentSection;
    private PrefUtils prefUtils;

    public IntroPresenter(PrefUtils prefUtils) {
        this.prefUtils = prefUtils;
        if (isFirstLaunch()) {
            setCurrentSection(0);
        } else {
            getViewState().openMainScreen();
        }
    }

    private boolean isFirstLaunch() {
        return prefUtils.getValue();
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
        prefUtils.putValue(false); //now this is not first launch
        getViewState().openMainScreen();
    }

    public void onPageSelected(int i) {
        setCurrentSection(i);
    }

}
