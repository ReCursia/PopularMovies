package com.recursia.popularmovies.presentation.views.contracts

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(SkipStrategy::class)
interface AuthScreenContract : MvpView {

    fun signIn(email: String, password: String)

    fun signUp(email: String, password: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setShowEmailValidationError(error: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setShowPasswordValidationError(error: Boolean)
}