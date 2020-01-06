package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.Screens;
import com.recursia.popularmovies.presentation.views.contracts.MainScreenContract;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainScreenPresenter extends MvpPresenter<MainScreenContract> {
    private final Router router;

    public MainScreenPresenter(Router router) {
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