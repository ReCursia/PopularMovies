package com.example.popularmovies.screens.detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {

    private int movieId;

    public DetailPresenter(int movieId) {
        this.movieId = movieId;
        setMovieData();
        setIconStatus();
    }

    private void setIconStatus() {
        /*
        if (favoriteMovie != null) {
            getViewState().setFavoriteIconOn();
        } else {
            getViewState().setFavoriteIconOff();
        }
        */
    }

    private void setMovieData() {
        //getViewState().setMovieDetail(movie);
    }

    public void onFavoriteIconClicked() {
        /*
        FavoriteMovie favoriteMovie = movieViewModel.getFavoriteMovieById(movieId);
        if (favoriteMovie == null) {
            Movie movie = movieViewModel.getMovieById(movieId);
            movieViewModel.insertFavoriteMovie(FavoriteMovie.fromMovie(movie));
            getViewState().setFavoriteIconOn();
            getViewState().showMovieAddedMessage();
        } else {
            movieViewModel.deleteFavoriteMovie(favoriteMovie);
            getViewState().setFavoriteIconOff();
            getViewState().showMovieRemovedMessage();
        }
        */
    }
}
