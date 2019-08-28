package com.recursia.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.recursia.popularmovies.models.pojo.Movie;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MoviesContract extends MvpView {

    //Loading
    @StateStrategyType(SkipStrategy.class)
    void showLoading();

    @StateStrategyType(SkipStrategy.class)
    void hideLoading();

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);

    //List
    void setMovies(List<Movie> movies);

    @StateStrategyType(AddToEndStrategy.class)
    void addMovies(List<Movie> movies);

    @StateStrategyType(SkipStrategy.class)
    void openMovieDetailInformation(Movie movie);

}
