package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.presentation.models.SectionItem
import com.recursia.popularmovies.presentation.views.contracts.IntroScreenContract
import com.recursia.popularmovies.utils.intro.FirstLaunchPreferences
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Intro screen presenter
 */
@InjectViewState
class IntroScreenPresenter(
        private val prefUtils: FirstLaunchPreferences, //first launch persistent data
        private val sectionItems: List<SectionItem>, //section items
        private val router: Router // router for navigating
) : MvpPresenter<IntroScreenContract>() {
    private var currentSection: Int = 0 // current section

    //to check it is first launch
    private val isFirstLaunch: Boolean
        get() = prefUtils.isFirstLaunch

    //to check if it is initial position
    private val isInitialPosition: Boolean
        get() = currentSection == 0

    //check is in range
    private val isInRangeExclude: Boolean
        get() = currentSection > 0 && currentSection < sectionsCount - 1

    //sections count
    private val sectionsCount: Int
        get() = sectionItems.size

    //to check if in end position
    private val isEndPosition: Boolean
        get() = currentSection == sectionsCount - 1

    /**
     * Calls on first view attach
     */
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (isFirstLaunch) {
            initViewState()
        } else {
            router.newRootScreen(Screens.MainScreen())
        }
    }

    /**
     * Function to init view
     */
    private fun initViewState() {
        viewState.setViewPagerData(sectionItems)
        setCurrentSection(0)
    }

    /**
     * Function to set current section
     * @param index section to set
     */
    private fun setCurrentSection(index: Int) {
        currentSection = index
        when {
            isInitialPosition -> {
                viewState.hidePreviousButton()
                viewState.showNextButton()
                viewState.hideFinishButton()
            }
            isInRangeExclude -> {
                viewState.showPreviousButton()
                viewState.showNextButton()
                viewState.hideFinishButton()
            }
            isEndPosition -> {
                viewState.showPreviousButton()
                viewState.hideNextButton()
                viewState.showFinishButton()
            }
        }
    }

    /**
     * On next button clicked callback
     */
    fun onNextButtonClicked() {
        viewState.setNextSection()
    }

    /**
     * On previous button clicked callback
     */
    fun onPreviousButtonClicked() {
        viewState.setPreviousSection()
    }

    /**
     * On finish button clicked callback
     */
    fun onFinishButtonClicked() {
        prefUtils.setFirstLaunch(false)
        router.newRootScreen(Screens.MainScreen())
    }

    /**
     * On page selected should set current section
     */
    fun onPageSelected(i: Int) {
        setCurrentSection(i)
    }
}
