package com.example.popularmovies.screens.favorite;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.pojo.FavoriteMovie;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.viewmodel.MovieViewModel;
import com.example.popularmovies.viewmodel.ViewModelDataChangedListener;

import java.util.List;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteContract> {
    private MovieViewModel movieViewModel;

    public FavoritePresenter(MovieViewModel movieViewModel) {
        this.movieViewModel = movieViewModel;
        initViewModel();
    }

    private void initViewModel() {
        movieViewModel.setDataChangedListener(new ViewModelDataChangedListener() {
            @Override
            public void moviesDataChanged(List<Movie> movieList) {
                //separate them
            }

            @Override
            public void favoriteMoviesDataChanged(List<FavoriteMovie> favoriteMovieList) {
                getViewState().setMovies(favoriteMovieList);
            }
        });
    }
}
