package com.recursia.popularmovies.presentation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.recursia.popularmovies.presentation.views.contracts.PopularScreenContract

import ru.terrakok.cicerone.Router

@InjectViewState
class PopularScreenPresenter(
    private val router: Router // TODO need some logic further?
) : MvpPresenter<PopularScreenContract>()
