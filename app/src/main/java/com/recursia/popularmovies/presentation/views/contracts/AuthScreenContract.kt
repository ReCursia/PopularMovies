package com.recursia.popularmovies.presentation.views.contracts

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(AddToEndSingleStrategy::class)
interface AuthScreenContract : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showProgressingDialog()

    @StateStrategyType(SkipStrategy::class)
    fun hideProgressingDialog()

    @StateStrategyType(SkipStrategy::class)
    fun showErrorMessage(message: String)
}