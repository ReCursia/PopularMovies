package com.example.popularmovies.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.adapters.movies.MoviesAdapter;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.repository.MoviesService;
import com.example.popularmovies.screens.detail.DetailActivity;
import com.example.popularmovies.screens.favorite.FavoriteActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements MainContract {

    @BindView(R.id.switchFilter)
    Switch switchFilter;
    @BindView(R.id.recyclerViewPosters)
    RecyclerView recyclerView;
    @BindView(R.id.popularTextView)
    TextView popularTextView;
    @BindView(R.id.ratedTextView)
    TextView ratedTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @InjectPresenter
    MainPresenter presenter;
    private MoviesAdapter moviesAdapter;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(MoviesService.getInstance().getMoviesApi());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemFavorite:
                presenter.onItemFavoriteClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.popularTextView)
    public void onPopularTextViewClicked() {
        presenter.onPopularTextViewClicked();
    }

    @OnClick(R.id.ratedTextView)
    public void onRatedTextViewClicked() {
        presenter.onRatedTextViewClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSwitch();
        initRecyclerView();
        initAdapter();
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.setClickListener(position -> {
            presenter.onMovieClicked(position);
        });
        recyclerView.setAdapter(moviesAdapter);
    }

    private void initSwitch() {
        switchFilter.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    presenter.onSwitchValueChanged(isChecked);
                }
        );
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottomReached = !recyclerView.canScrollVertically(1);
                if (isBottomReached) {
                    presenter.bottomIsReached();
                }
            }
        });
    }

    @Override
    public void setSwitchOff() {
        switchFilter.setChecked(false);
    }

    @Override
    public void setSwitchOn() {
        switchFilter.setChecked(true);
    }

    @Override
    public void showLoading() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMovies(List<Movie> movies) {
        moviesAdapter.setMovies(movies);
    }

    @Override
    public void addMovies(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
    }

    @Override
    public void setPopularTextColor(int color) {
        popularTextView.setTextColor(getResources().getColor(color));
    }

    @Override
    public void setRatedTextColor(int color) {
        ratedTextView.setTextColor(getResources().getColor(color));
    }

    @Override
    public void openFavoriteScreen() {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    @Override
    public void openDetailScreen(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", moviesAdapter.getItem(position).getId());
        startActivity(intent);
    }
}
