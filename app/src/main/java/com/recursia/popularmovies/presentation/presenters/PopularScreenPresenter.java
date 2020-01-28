package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.Screens;
import com.recursia.popularmovies.presentation.views.contracts.PopularScreenContract;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class PopularScreenPresenter extends MvpPresenter<PopularScreenContract> {
    private final Router router;

    public PopularScreenPresenter(Router router) {
        this.router = router;
    }

    public void onItemFavoriteClicked() {
        router.navigateTo(new Screens.FavoriteScreen());
    }

    public void onItemAboutClicked() {
        getViewState().openAboutDialog();
    }

    public void onDismissDialog() {
        getViewState().hideAboutDialog();
    }

    public void onPositiveDialogButtonClicked() {
        getViewState().openGooglePlayPage();
    }

    public void onNegativeDialogButtonClicked() {
        getViewState().hideAboutDialog();
    }

    public void onItemSearchClicked() {
        router.navigateTo(new Screens.SearchScreen());
    }

}