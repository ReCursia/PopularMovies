package com.example.popularmovies.screens.detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.pojo.MovieTrailers;
import com.example.popularmovies.repository.MoviesApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailContract> {
    private MoviesApi client;
    private int movieId;

    public DetailPresenter(MoviesApi client, int movieId) {
        this.client = client;
        this.movieId = movieId;
        setMovieData();
        setIconStatus();
        initTrailers();
    }

    private void initTrailers() {
        Call<MovieTrailers> call = client.getMovieTrailersById(movieId);
        call.enqueue(new Callback<MovieTrailers>() {
            @Override
            public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {
                if (response.isSuccessful()) {
                    getViewState().setTrailers(response.body().getTrailers());
                }
            }

            @Override
            public void onFailure(Call<MovieTrailers> call, Throwable t) {
                getViewState().showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void setIconStatus() {
        /*
        if (favoriteMovie != null) {
            getViewState().setFavoriteIconOn();
        } else {
            getViewState().setFavoriteIconOff();
        }
        */
    }

    private void setMovieData() {
        //getViewState().setMovieDetail(movie);
    }

    public void onFavoriteIconClicked() {
        /*
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
        */
    }

    public void onTrailerPlayButtonClicked(int position) {
        getViewState().openTrailerUrl(position);
    }
}
