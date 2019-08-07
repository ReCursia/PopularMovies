package com.example.popularmovies.screens.detail;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.pojo.MovieTrailers;
import com.example.popularmovies.pojo.Trailer;
import com.example.popularmovies.repository.MoviesApi;

import java.util.List;

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
        getViewState().hideTrailers();
        initTrailers();
        initMovieData();
    }

    private void initTrailers() {
        Call<MovieTrailers> call = client.getMovieTrailersById(movieId);
        call.enqueue(new Callback<MovieTrailers>() {
            @Override
            public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {
                if (response.isSuccessful()) {
                    handleSuccessfulResponse(response);
                }
            }

            @Override
            public void onFailure(Call<MovieTrailers> call, Throwable t) {
                getViewState().showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void handleSuccessfulResponse(Response<MovieTrailers> response) {
        List<Trailer> trailers = response.body().getTrailers();
        if (!trailers.isEmpty()) {
            getViewState().setTrailers(trailers);
            getViewState().showTrailers();
        } else {
            getViewState().hideTrailers();
        }
    }

    private void initMovieData() {
        Call<Movie> call = client.getMovieById(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    getViewState().setMovieDetail(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                getViewState().showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    public void onFavoriteIconClicked() {
        getViewState().setFavoriteIconOn();
        getViewState().showMovieAddedMessage();
        //TODO implement
    }

    public void onTrailerPlayButtonClicked(int position) {
        getViewState().openTrailerUrl(position);
    }

    public void menuIsInflated() {
        setDefaultFavoriteIcon();
    }

    private void setDefaultFavoriteIcon() {
        //TODO implement
        getViewState().setFavoriteIconOff();
    }

}
