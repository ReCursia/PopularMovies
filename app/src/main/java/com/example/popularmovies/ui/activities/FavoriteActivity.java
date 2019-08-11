package com.example.popularmovies.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.models.database.MovieDatabase;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.presenters.FavoritePresenter;
import com.example.popularmovies.ui.adapters.movies.MoviesAdapter;
import com.example.popularmovies.views.FavoriteContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends MvpAppCompatActivity implements FavoriteContract {
    private static final int SPAN_COUNT = 3;

    @BindView(R.id.favoriteRecycleView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.noFavoriteMoviesScreen)
    View noFavoriteMoviesScreen;
    @InjectPresenter
    FavoritePresenter presenter;
    MoviesAdapter moviesAdapter;

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

    @ProvidePresenter
    FavoritePresenter providePresenter() {
        return new FavoritePresenter(MovieDatabase.getInstance(this).movieDao());
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
        initActionBar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.favorite_title));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.setClickListener(item -> presenter.onItemClicked(item));
        recyclerView.setAdapter(moviesAdapter);
    }

    private void initActionBar() {
        //add back arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void openDetailScreen(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", movie.getId());
        startActivity(intent);
    }

}
