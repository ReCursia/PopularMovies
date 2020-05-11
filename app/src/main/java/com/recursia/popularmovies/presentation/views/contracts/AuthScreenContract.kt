package com.recursia.popularmovies.presentation.views.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AuthScreenContract : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showProgressingDialog()

    @StateStrategyType(SkipStrategy::class)
    fun hideProgressingDialog()

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)
}