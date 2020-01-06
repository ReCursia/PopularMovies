package com.recursia.popularmovies.presentation.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.TheApplication;
import com.recursia.popularmovies.di.AppComponent;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.presenters.SearchScreenPresenter;
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter;
import com.recursia.popularmovies.presentation.views.contracts.SearchScreenContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchScreenFragment extends MvpAppCompatFragment implements SearchScreenContract {
    private static final int SPAN_COUNT = 2;

    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @InjectPresenter
    SearchScreenPresenter presenter;
    private MoviesAdapter moviesAdapter;

    public static SearchScreenFragment getInstance() {
        return new SearchScreenFragment();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @ProvidePresenter
    SearchScreenPresenter providePresenter() {
        AppComponent app = TheApplication.getInstance().getAppComponent();
        return new SearchScreenPresenter(app.getSearchScreenInteractor(), app.getRouter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initRecyclerView();
        initAdapter();
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.search_movie_title));
        toolbar.setBackgroundColor(getResources().getColor(R.color.black));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> presenter.onBackPressed());
        toolbar.inflateMenu(R.menu.search_menu);
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.searchItem);
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
    }

    private void initRecyclerView() {
        searchRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        searchRecyclerView.setHasFixedSize(true);
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(getContext(), false);
        moviesAdapter.setClickListener(item -> presenter.onItemClicked(item));
        searchRecyclerView.setAdapter(moviesAdapter);
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
