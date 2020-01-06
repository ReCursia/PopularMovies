package com.recursia.popularmovies.presentation.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.recursia.popularmovies.presentation.presenters.FavoriteScreenPresenter;
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter;
import com.recursia.popularmovies.presentation.views.contracts.FavoriteScreenContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteScreenFragment extends MvpAppCompatFragment implements FavoriteScreenContract {
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

    public static FavoriteScreenFragment getInstance() {
        return new FavoriteScreenFragment();
    }

    @ProvidePresenter
    FavoriteScreenPresenter providePresenter() {
        AppComponent app = TheApplication.getInstance().getAppComponent();
        return new FavoriteScreenPresenter(app.getFavoriteScreenInteractor(), app.getRouter());
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
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initRecyclerView();
        initAdapter();
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> presenter.onBackPressed());
        toolbar.setBackgroundColor(getResources().getColor(R.color.black));
        toolbar.setTitle(getString(R.string.favorite_item));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        recyclerView.setHasFixedSize(true); //items are same height
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(getContext(), IS_RECOMMENDATION_MOVIES);
        moviesAdapter.setClickListener(item -> presenter.onItemClicked(item));
        recyclerView.setAdapter(moviesAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
