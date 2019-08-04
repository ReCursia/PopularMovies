package com.example.popularmovies.screens.favorite;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MovieAdapter;
import com.example.popularmovies.pojo.FavoriteMovie;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.viewmodel.MovieViewModelFactory;
import com.example.popularmovies.viewmodel.MovieViewModelImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends MvpAppCompatActivity implements FavoriteContract {
    @BindView(R.id.favoriteRecycleView)
    RecyclerView recyclerView;

    @InjectPresenter
    FavoritePresenter presenter;
    MovieAdapter movieAdapter;

    @ProvidePresenter
    FavoritePresenter providePresenter() {
        MovieViewModelFactory factory = new MovieViewModelFactory(getApplication(), this);
        return new FavoritePresenter(ViewModelProviders.of(this, factory).get(MovieViewModelImpl.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        initRecyclerView();
        initAdapter();
    }

    private void initAdapter() {
        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void setMovies(List<FavoriteMovie> favoriteMovies) {
        List<Movie> movies = new ArrayList<Movie>();
        movies.addAll(favoriteMovies);
        movieAdapter.setMovies(movies);
    }
}
