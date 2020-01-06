package com.recursia.popularmovies.presentation.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SearchScreenContract extends MvpView {

    void setMovies(List<Movie> movies);

    void addMovies(List<Movie> movies);

    @StateStrategyType(SkipStrategy.class)
    void openDetailScreen(Movie movie);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);

}
