package com.example.popularmovies.presenters;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.network.MoviesApi;
import com.example.popularmovies.pojo.DiscoverMovies;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.views.MainContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    private void makeAccentRatedText() {
        getViewState().setPopularTextColor(R.color.white);
        getViewState().setRatedTextColor(R.color.colorAccent);
    }

    private void makeAccentPopularText() {
        getViewState().setPopularTextColor(R.color.colorAccent);
        getViewState().setRatedTextColor(R.color.white);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void loadMovies() {
        if (tabIsChanged) getViewState().showLoading();
        client.discoverMovies(sortBy, currentPage, NetworkUtils.getDefaultLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse); //Reflection
    }

    private void handleSuccessfulResponse(DiscoverMovies discoverMovies) {
        List<Movie> movies = discoverMovies.getMovies();
        if (tabIsChanged) {
            getViewState().setMovies(movies);
            getViewState().hideLoading();
            tabIsChanged = false;
        } else {
            getViewState().addMovies(movies);
        }
        currentPage++;
    }

    private void handleErrorResponse(Throwable t) {
        getViewState().hideLoading();
        getViewState().showErrorMessage(t.getLocalizedMessage());
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

    public void onMovieClicked(int position) {
        getViewState().openDetailScreen(position);
    }

    public void onItemFavoriteClicked() {
        getViewState().openFavoriteScreen();
    }

    public void onItemAboutClicked() {
        getViewState().openAboutDialog();
    }

    public void onDismissDialog() {
        getViewState().hideAboutDialog();
    }

    public void onPositiveDialogButtonClicked() {
        getViewState().openGooglePlayPage();
    }

    public void onNegativeDialogButtonClicked() {
        getViewState().hideAboutDialog();
    }
}