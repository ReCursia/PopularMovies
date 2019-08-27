package com.recursia.popularmovies.ui.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.presenters.MainPresenter;
import com.recursia.popularmovies.ui.adapters.MoviesPagerAdapter;
import com.recursia.popularmovies.ui.fragments.MoviesFragment;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.utils.TagUtils;
import com.recursia.popularmovies.views.MainContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainContract, MoviesFragment.OnFragmentMoviesInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.movies_view_pager)
    ViewPager moviesViewPager;

    @InjectPresenter
    MainPresenter presenter;
    private AlertDialog aboutDialog;

    @Override
    public void openGooglePlayPage() {
        Uri uri = Uri.parse(NetworkUtils.GOOGLE_PLAY_NATIVE + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            //Trying to open installed Google play
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            //Opening web version
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initViewPager();
        initTabLayout();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initViewPager() {
        MoviesPagerAdapter pagerAdapter = new MoviesPagerAdapter(getSupportFragmentManager());
        moviesViewPager.setAdapter(pagerAdapter);
    }

    private void initTabLayout() {
        tabLayout.setupWithViewPager(moviesViewPager);
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
    public void openFavoriteScreen() {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    @Override
    public void openDetailScreen(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(TagUtils.MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @Override
    public void onFragmentMovieClicked(Movie movie) {
        presenter.onMovieClicked(movie);
    }

}