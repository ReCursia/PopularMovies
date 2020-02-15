package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.recursia.popularmovies.presentation.models.SectionItem

@StateStrategyType(AddToEndSingleStrategy::class)
interface IntroScreenContract : MvpView {

    fun setNextSection()

    fun setPreviousSection()

    //Next button
    fun showNextButton()

    fun hideNextButton()

    //Prev button
    fun showPreviousButton()

    fun hidePreviousButton()

    //Finish button
    fun showFinishButton()

    fun hideFinishButton()

    //View pager
    fun setViewPagerData(sectionItems: List<SectionItem>)

}
