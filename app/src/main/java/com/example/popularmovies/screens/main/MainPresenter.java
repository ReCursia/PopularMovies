package com.example.popularmovies.screens.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.pojo.DiscoverMovies;
import com.example.popularmovies.pojo.FavoriteMovie;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.repository.MoviesApi;
import com.example.popularmovies.repository.MoviesService;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.viewmodel.MovieViewModel;
import com.example.popularmovies.viewmodel.ViewModelDataChangedListener;

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
    private MovieViewModel movieViewModel;

    public MainPresenter(MovieViewModel movieViewModel) {
        this.movieViewModel = movieViewModel;
        initViewModel();
        client = MoviesService.getInstance().getMoviesApi();
        getViewState().setSwitchOff();
        //onSwitchValueChanged(false);
    }

    private void initViewModel() {
        movieViewModel.setDataChangedListener(new ViewModelDataChangedListener() {
            @Override
            public void moviesDataChanged(List<Movie> movieList) {
                if (tabIsChanged) {
                    getViewState().setMovies(movieList);
                    tabIsChanged = false;
                }

                getViewState().addMovies(movieList);
            }

            @Override
            public void favoriteMoviesDataChanged(List<FavoriteMovie> favoriteMovieList) {
                //nothing yet SEPARATE CLASSES FAST
            }
        });
    }

    private void loadMovies() {
        Call<DiscoverMovies> call = client.discoverMovies(sortBy, currentPage);

        if (tabIsChanged)
            getViewState().showLoading(); //TODO небольшой костыль, но я экспериментирую

        call.enqueue(new Callback<DiscoverMovies>() {
            @Override
            public void onResponse(Call<DiscoverMovies> call, Response<DiscoverMovies> response) {
                if (response.isSuccessful()) {
                    if (tabIsChanged) getViewState().hideLoading();
                    moviesAreLoaded(response.body().getMovies());
                    currentPage++;
                }
            }

            @Override
            public void onFailure(Call<DiscoverMovies> call, Throwable t) {
                getViewState().showErrorMessage(t.getMessage());
                //movieViewModel.getAllMovies(); //get it from DB if no connection данные скорее всего изменняются, но так как листенер уже отработал то ничего не оповестили
            }
        });
    }

    private void moviesAreLoaded(List<Movie> movies) {
        if (tabIsChanged) {
            movieViewModel.deleteAllMovies();
        }
        movieViewModel.insertMovies(movies);
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

    public void onItemMainClicked() {
        getViewState().openMainScreen();
    }
}