package com.example.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.popularmovies.models.pojo.Cast;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.Trailer;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DetailContract extends MvpView {

    //Movie detail
    void setMovieDetail(Movie movie);

    void hideMovieDetail();

    void showMovieDetail();

    //Favorite icon
    void setFavoriteIconOn();

    void setFavoriteIconOff();

    //Share
    void shareMovie(Movie movie);

    //Cast
    void setCast(List<Cast> cast);

    void showCast();

    void hideCast();

    void showMovies();

    void hideMovies();

    @StateStrategyType(SkipStrategy.class)
    void openDetailScreen(Movie movie);

    //Trailers
    void setTrailers(List<Trailer> trailers);

    void hideTrailers();

    void showTrailers();

    //Genres
    void setGenres(List<Genre> genres);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void showMovieAddedMessage();

    @StateStrategyType(SkipStrategy.class)
    void showMovieRemovedMessage();

    @StateStrategyType(SkipStrategy.class)
    void openTrailerUrl(Trailer trailer);

    void setRecommendationMovies(List<Movie> movies);

    //Movie recommendations

}
