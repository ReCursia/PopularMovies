package com.example.popularmovies.screens.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.pojo.DiscoverMovies;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.repository.MoviesApi;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainContract> {

    private MoviesApi client;
    private int currentPage;
    private String sortBy;
    private boolean tabIsChanged;

    public MainPresenter(MoviesApi client) {
        this.client = client;
        getViewState().setSwitchOff();
        onSwitchValueChanged(false);
    }

    private void loadMovies() {
        Call<DiscoverMovies> call = client.discoverMovies(sortBy, currentPage);

        if (tabIsChanged) {
            getViewState().showLoading();
        }

        call.enqueue(new Callback<DiscoverMovies>() {
            @Override
            public void onResponse(Call<DiscoverMovies> call, Response<DiscoverMovies> response) {
                if (response.isSuccessful()) {
                    handleSuccessfulResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DiscoverMovies> call, Throwable t) {
                getViewState().hideLoading();
                getViewState().showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void handleSuccessfulResponse(Response<DiscoverMovies> response) {
        List<Movie> movies = response.body().getMovies();
        if (tabIsChanged) {
            getViewState().setMovies(movies);
            getViewState().hideLoading();
            tabIsChanged = false;
        } else {
            getViewState().addMovies(movies);
        }
        currentPage++;
    }

    public void onPopularTextViewClicked() {
        getViewState().setSwitchOff();
    }

    public void onRatedTextViewClicked() {
        getViewState().setSwitchOn();
    }

    public void bottomIsReached() {
        loadMovies();
    }

    public void onSwitchValueChanged(boolean isChecked) {
        if (isChecked) {
            makeAccentRatedText();
            sortBy = NetworkUtils.TOP_RATED;
        } else {
            makeAccentPopularText();
            sortBy = NetworkUtils.POPULARITY;
        }
        tabIsChanged = true;
        currentPage = 1;
        loadMovies();
    }

    private void makeAccentPopularText() {
        getViewState().setPopularTextColor(R.color.colorAccent);
        getViewState().setRatedTextColor(R.color.white);
    }

    private void makeAccentRatedText() {
        getViewState().setPopularTextColor(R.color.white);
        getViewState().setRatedTextColor(R.color.colorAccent);
    }

    public void onMovieClicked(int position) {
        getViewState().openDetailScreen(position);
    }

    public void onItemFavoriteClicked() {
        getViewState().openFavoriteScreen();
    }
}