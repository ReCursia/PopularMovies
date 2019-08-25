package com.recursia.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.views.MainContract;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainContract> {

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

}