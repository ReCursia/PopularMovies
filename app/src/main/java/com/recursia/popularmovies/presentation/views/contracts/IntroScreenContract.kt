package com.recursia.popularmovies.presentation.views.contracts

import com.recursia.popularmovies.presentation.models.SectionItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IntroScreenContract : MvpView {

    fun setNextSection()

    fun setPreviousSection()

    // Next button
    fun showNextButton()

    fun hideNextButton()

    // Prev button
    fun showPreviousButton()

    fun hidePreviousButton()

    // Finish button
    fun showFinishButton()

    fun hideFinishButton()

    // View pager
    fun setViewPagerData(sectionItems: List<SectionItem>)
}
