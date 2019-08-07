package com.example.popularmovies.screens.favorite;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.popularmovies.pojo.Movie;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface FavoriteContract extends MvpView {
    //List
    void setMovies(List<Movie> movies);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);
}
