package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.Screens;
import com.recursia.popularmovies.presentation.models.SectionItem;
import com.recursia.popularmovies.presentation.views.contracts.IntroScreenContract;
import com.recursia.popularmovies.utils.intro.PrefUtils;

import java.util.List;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class IntroScreenPresenter extends MvpPresenter<IntroScreenContract> {
    private final PrefUtils prefUtils;
    private final List<SectionItem> sectionItems;
    private final Router router;
    private int currentSection;

    public IntroScreenPresenter(PrefUtils prefUtils, List<SectionItem> sectionItems, Router router) {
        this.prefUtils = prefUtils;
        this.sectionItems = sectionItems;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (isFirstLaunch()) {
            initViewState();
        } else {
            router.newRootScreen(new Screens.MainScreen());
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
        router.newRootScreen(new Screens.MainScreen());
    }

    public void onPageSelected(int i) {
        setCurrentSection(i);
    }

}
