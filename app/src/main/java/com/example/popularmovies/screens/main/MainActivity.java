package com.example.popularmovies.screens.main;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.popularmovies.network.MoviesService;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.screens.detail.DetailActivity;
import com.example.popularmovies.screens.favorite.FavoriteActivity;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements MainContract {
    private static final int SPAN_COUNT = 3;
    private static final int DIRECTION_UP = 1;

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @InjectPresenter
    MainPresenter presenter;
    private MoviesAdapter moviesAdapter;
    private AlertDialog aboutDialog;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(MoviesService.getInstance().getMoviesApi());
    }

    @Override
    public void openGooglePlayPage() {
        Uri uri = Uri.parse(NetworkUtils.GOOGLE_PLAY_NATIVE + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(NetworkUtils.GOOGLE_PLAY_URL + getPackageName())));
        }
    }

    @Override
    public void openAboutDialog() {
        aboutDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.about))
                .setMessage(getString(R.string.about_description))
                .setPositiveButton(getString(R.string.rate_app_dialog_positive_button), (dialog, which) -> presenter.onPositiveDialogButtonClicked())
                .setNegativeButton(getString(R.string.rate_app_negative_button), (dialog, which) -> presenter.onNegativeDialogButtonClicked())
                .setOnDismissListener(dialog -> presenter.onDismissDialog())
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void hideAboutDialog() {
        if (aboutDialog != null) {
            aboutDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemFavorite:
                presenter.onItemFavoriteClicked();
                break;
            case R.id.itemAbout:
                presenter.onItemAboutClicked();
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
        initToolbar();
        initSwitch();
        initRecyclerView();
        initAdapter();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initSwitch() {
        switchFilter.setOnCheckedChangeListener(
                (buttonView, isChecked) -> presenter.onSwitchValueChanged(isChecked));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottomReached = !recyclerView.canScrollVertically(DIRECTION_UP);
                if (isBottomReached) {
                    presenter.bottomIsReached();
                }
            }
        });
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.setClickListener(position -> presenter.onMovieClicked(position));
        recyclerView.setAdapter(moviesAdapter);
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
    protected void onDestroy() {
        super.onDestroy();
        if (aboutDialog != null) {
            aboutDialog.setOnDismissListener(null);
            aboutDialog.dismiss();
        }
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
