package com.recursia.popularmovies.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.models.network.MoviesService;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.presenters.SearchPresenter;
import com.recursia.popularmovies.ui.adapters.MoviesAdapter;
import com.recursia.popularmovies.utils.TagUtils;
import com.recursia.popularmovies.views.SearchContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends MvpAppCompatActivity implements SearchContract {
    private static final int SPAN_COUNT = 2;

    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @InjectPresenter
    SearchPresenter presenter;
    private MoviesAdapter moviesAdapter;

    @Override
    public void openDetailScreen(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(TagUtils.MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @ProvidePresenter
    SearchPresenter providePresenter() {
        return new SearchPresenter(MoviesService.getInstance().getMoviesApi());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();
        initAdapter();
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(this, false);
        moviesAdapter.setClickListener(item -> presenter.onItemClicked(item));
        searchRecyclerView.setAdapter(moviesAdapter);
    }

    private void initRecyclerView() {
        searchRecyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        searchRecyclerView.setHasFixedSize(true);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.search_movie_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchItem);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                presenter.onQueryTextChanged(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setMovies(List<Movie> movies) {
        moviesAdapter.setMovies(movies);
    }

    @Override
    public void addMovies(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
    }
}
