package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.presentation.models.SectionItem
import com.recursia.popularmovies.presentation.views.contracts.IntroScreenContract
import com.recursia.popularmovies.utils.intro.FirstLaunchPreferences
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class IntroScreenPresenter(
    private val prefUtils: FirstLaunchPreferences,
    private val sectionItems: List<SectionItem>,
    private val router: Router
) : MvpPresenter<IntroScreenContract>() {
    private var currentSection: Int = 0

    private val isFirstLaunch: Boolean
        get() = prefUtils.isFirstLaunch

    private val isInitialPosition: Boolean
        get() = currentSection == 0

    private val isInRangeExclude: Boolean
        get() = currentSection > 0 && currentSection < sectionsCount - 1

    private val sectionsCount: Int
        get() = sectionItems.size

    private val isEndPosition: Boolean
        get() = currentSection == sectionsCount - 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (isFirstLaunch) {
            initViewState()
        } else {
            router.newRootScreen(Screens.MainScreen())
        }
    }

    private fun initViewState() {
        viewState.setViewPagerData(sectionItems)
        setCurrentSection(0)
    }

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

    fun onNextButtonClicked() {
        viewState.setNextSection()
    }

    fun onPreviousButtonClicked() {
        viewState.setPreviousSection()
    }

    fun onFinishButtonClicked() {
        prefUtils.setFirstLaunch(false)
        router.newRootScreen(Screens.MainScreen())
    }

    fun onPageSelected(i: Int) {
        setCurrentSection(i)
    }
}
