package com.example.popularmovies.screens.detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.pojo.FavoriteMovie;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.viewmodel.MovieViewModel;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {

    private MovieViewModel movieViewModel;
    private int movieId;

    public DetailPresenter(MovieViewModel movieViewModel, int movieId) {
        this.movieViewModel = movieViewModel;
        this.movieId = movieId;
        setMovieData();
        setIconStatus();
    }

    private void setIconStatus() {
        FavoriteMovie favoriteMovie = movieViewModel.getFavoriteMovieById(movieId);
        if (favoriteMovie != null) {
            getViewState().setFavoriteIconOn();
        } else {
            getViewState().setFavoriteIconOff();
        }
    }

    private void setMovieData() {
        Movie movie = movieViewModel.getMovieById(movieId);
        getViewState().setMovieDetail(movie);
    }

    public void onFavoriteIconClicked() {
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
    }
}
