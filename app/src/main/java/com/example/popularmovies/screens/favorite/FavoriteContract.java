package com.example.popularmovies.screens.favorite;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface FavoriteContract extends MvpView {
    //List
    //void setMovies(List<FavoriteMovie> movies);
}
