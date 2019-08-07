package com.example.popularmovies.screens.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.adapters.OnItemClickListener;
import com.example.popularmovies.adapters.movies.MoviesAdapter;
import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.screens.detail.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends MvpAppCompatActivity implements FavoriteContract {
    private static final int SPAN_COUNT = 2;

    @BindView(R.id.favoriteRecycleView)
    RecyclerView recyclerView;
    @InjectPresenter
    FavoritePresenter presenter;
    MoviesAdapter moviesAdapter;

    @Override
    public void setMovies(List<Movie> movies) {
        moviesAdapter.setMovies(movies);
    }

    @ProvidePresenter
    FavoritePresenter providePresenter() {
        return new FavoritePresenter(MovieDatabase.getInstance(this).movieDao());
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
        initRecyclerView();
        initAdapter();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.onItemClicked(position);
            }
        });
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public void openDetailScreen(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", moviesAdapter.getItem(position).getId());
        startActivity(intent);
    }

}
