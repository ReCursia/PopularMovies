package com.example.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.popularmovies.pojo.Movie;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainContract extends MvpView {

    //Switch
    void setSwitchOff();

    void setSwitchOn();

    //Loading
    @StateStrategyType(SkipStrategy.class)
    void showLoading();

    @StateStrategyType(SkipStrategy.class)
    void hideLoading();

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);

    //List
    void setMovies(List<Movie> movies);

    void addMovies(List<Movie> movies);

    //Dialog

    void openAboutDialog();

    void hideAboutDialog();

    @StateStrategyType(SkipStrategy.class)
    void openGooglePlayPage();

    //Text
    void setPopularTextColor(int color);

    void setRatedTextColor(int color);

    @StateStrategyType(SkipStrategy.class)
    void openDetailScreen(int position);

    @StateStrategyType(SkipStrategy.class)
    void openFavoriteScreen();

}
