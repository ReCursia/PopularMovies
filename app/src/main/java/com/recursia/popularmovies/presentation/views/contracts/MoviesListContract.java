package com.recursia.popularmovies.presentation.views.contracts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MoviesListContract extends MvpView {

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

}
