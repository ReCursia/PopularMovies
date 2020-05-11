package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.presentation.views.contracts.AccountScreenContract
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class AccountScreenPresenter : MvpPresenter<AccountScreenContract>()