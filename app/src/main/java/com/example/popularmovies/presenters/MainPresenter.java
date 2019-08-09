package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.views.MainContract;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainContract> {

    public MainPresenter() {

    }

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