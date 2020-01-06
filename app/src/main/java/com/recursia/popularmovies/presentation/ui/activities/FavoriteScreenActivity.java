package com.recursia.popularmovies.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.TheApplication;
import com.recursia.popularmovies.di.AppComponent;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.presenters.FavoriteScreenPresenter;
import com.recursia.popularmovies.presentation.ui.adapters.MoviesAdapter;
import com.recursia.popularmovies.presentation.ui.navigation.Navigator;
import com.recursia.popularmovies.presentation.views.FavoriteScreenContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteScreenActivity extends MvpAppCompatActivity implements FavoriteScreenContract {
    private static final int SPAN_COUNT = 2;
    private static final boolean IS_RECOMMENDATION_MOVIES = false;

    @BindView(R.id.favoriteRecycleView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.noFavoriteMoviesScreen)
    View noFavoriteMoviesScreen;
    @InjectPresenter
    FavoriteScreenPresenter presenter;
    private MoviesAdapter moviesAdapter;

    @ProvidePresenter
    FavoriteScreenPresenter providePresenter() {
        AppComponent app = TheApplication.getInstance().getAppComponent();
        return new FavoriteScreenPresenter(app.getFavoriteScreenInteractor());
    }

    @Override
    public void showNoFavoriteScreen() {
        noFavoriteMoviesScreen.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideNoFavoriteScreen() {
        noFavoriteMoviesScreen.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovies(List<Movie> movies) {
        moviesAdapter.setMovies(movies);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();
        initAdapter();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        //add back arrow and title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.favorite_item));
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerView.setHasFixedSize(true); //items are same height
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(this, IS_RECOMMENDATION_MOVIES);
        moviesAdapter.setClickListener(item -> presenter.onItemClicked(item));
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public void openDetailScreen(Movie movie) {
        Navigator.openDetailScreen(this, movie.getId());
    }

}
