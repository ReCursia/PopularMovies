package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.pojo.SectionItem;
import com.example.popularmovies.utils.intro.PrefUtils;
import com.example.popularmovies.views.IntroContract;

import java.util.List;

@InjectViewState
public class IntroPresenter extends MvpPresenter<IntroContract> {
    private int currentSection;
    private PrefUtils prefUtils;
    private List<SectionItem> sectionItems;

    public IntroPresenter(List<SectionItem> sectionItems, PrefUtils prefUtils) {
        this.sectionItems = sectionItems;
        this.prefUtils = prefUtils;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (isFirstLaunch()) {
            initViewState();
        } else {
            getViewState().openMainScreen();
        }
    }

    private boolean isFirstLaunch() {
        return prefUtils.getValue();
    }

    private void initViewState() {
        getViewState().setViewPagerData(sectionItems);
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
        return (currentSection > 0) && (currentSection < (getSectionsCount() - 1));
    }

    private int getSectionsCount() {
        return sectionItems.size();
    }

    private boolean isEndPosition() {
        return currentSection == (getSectionsCount() - 1);
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
