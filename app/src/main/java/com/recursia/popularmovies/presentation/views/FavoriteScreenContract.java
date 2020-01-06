package com.recursia.popularmovies.presentation.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.recursia.popularmovies.domain.models.Movie;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface FavoriteScreenContract extends MvpView {

    //List
    void setMovies(List<Movie> movies);

    void showNoFavoriteScreen();

    void hideNoFavoriteScreen();

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void openDetailScreen(Movie movie);

}
