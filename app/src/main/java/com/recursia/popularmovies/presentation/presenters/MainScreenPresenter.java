package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.views.MainScreenContract;

@InjectViewState
public class MainScreenPresenter extends MvpPresenter<MainScreenContract> {

    public void onMovieClicked(Movie movie) {
        getViewState().openDetailScreen(movie);
    }

    public void onItemFavoriteClicked() {
        getViewState().openFavoriteScreen();
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
        getViewState().openSearchScreen();
    }

}