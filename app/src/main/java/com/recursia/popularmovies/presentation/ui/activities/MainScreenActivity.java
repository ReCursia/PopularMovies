package com.recursia.popularmovies.presentation.ui.activities;

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
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.presenters.MainScreenPresenter;
import com.recursia.popularmovies.presentation.ui.adapters.MoviesPagerAdapter;
import com.recursia.popularmovies.presentation.ui.fragments.MoviesListFragment;
import com.recursia.popularmovies.presentation.ui.navigation.Navigator;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.presentation.views.MainScreenContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenActivity extends MvpAppCompatActivity implements MainScreenContract, MoviesListFragment.OnFragmentMoviesInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.movies_view_pager)
    ViewPager moviesViewPager;
    @InjectPresenter
    MainScreenPresenter presenter;
    private AlertDialog aboutDialog;

    @Override
    public void openSearchScreen() {
        Navigator.openSearchScreen(this);
    }

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
                .setTitle(getString(R.string.about_item))
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
            case R.id.searchItem:
                presenter.onItemSearchClicked();
                break;
            case R.id.favoriteItem:
                presenter.onItemFavoriteClicked();
                break;
            case R.id.aboutItem:
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
        Navigator.openFavoriteScreen(this);
    }

    @Override
    public void openDetailScreen(Movie movie) {
        Navigator.openDetailScreen(this, movie.getId());
    }

    @Override
    public void onFragmentMovieClicked(Movie movie) {
        presenter.onMovieClicked(movie);
    }

}
