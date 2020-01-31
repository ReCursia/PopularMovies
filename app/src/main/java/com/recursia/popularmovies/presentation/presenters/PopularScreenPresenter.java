package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.presentation.views.contracts.PopularScreenContract;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class PopularScreenPresenter extends MvpPresenter<PopularScreenContract> {
    private final Router router; //TODO need some logic further?

    public PopularScreenPresenter(Router router) {
        this.router = router;
    }

}