package com.recursia.popularmovies.presentation.views.fragments;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.TheApplication;
import com.recursia.popularmovies.di.AppComponent;
import com.recursia.popularmovies.presentation.presenters.PopularScreenPresenter;
import com.recursia.popularmovies.presentation.views.adapters.MoviesPagerAdapter;
import com.recursia.popularmovies.presentation.views.contracts.PopularScreenContract;
import com.recursia.popularmovies.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularScreenFragment extends MvpAppCompatFragment implements PopularScreenContract {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.movies_view_pager)
    ViewPager moviesViewPager;
    @InjectPresenter
    PopularScreenPresenter presenter;
    private AlertDialog aboutDialog;

    public static PopularScreenFragment getInstance() {
        return new PopularScreenFragment();
    }

    @ProvidePresenter
    PopularScreenPresenter providePresenter() {
        AppComponent app = TheApplication.getInstance().getAppComponent();
        return new PopularScreenPresenter(app.getRouter());
    }

    @Override
    public void openGooglePlayPage() {
        Uri uri = Uri.parse(NetworkUtils.GOOGLE_PLAY_NATIVE + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            //Trying to open installed Google play
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            //Opening web version
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(NetworkUtils.GOOGLE_PLAY_URL + getContext().getPackageName())));
        }
    }

    @Override
    public void openAboutDialog() {
        aboutDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.about_item))
                .setMessage(getString(R.string.about_description))
                .setPositiveButton(getString(R.string.rate_app_dialog_positive_button), (dialog, which) -> presenter.onPositiveDialogButtonClicked())
                .setNegativeButton(getString(R.string.rate_app_negative_button), (dialog, which) -> presenter.onNegativeDialogButtonClicked())
                .setOnDismissListener(dialog -> presenter.onDismissDialog())
                .show();
    }

    @Override
    public void hideAboutDialog() {
        if (aboutDialog != null) {
            aboutDialog.dismiss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initViewPager();
        initTabLayout();
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitle(R.string.app_name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.black));
        toolbar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.searchItem:
                    presenter.onItemSearchClicked();
                    return true;
                case R.id.favoriteItem:
                    presenter.onItemFavoriteClicked();
                    return true;
                case R.id.aboutItem:
                    presenter.onItemAboutClicked();
                    return true;
                default:
                    return false;
            }

        });

    }

    private void initViewPager() {
        MoviesPagerAdapter pagerAdapter = new MoviesPagerAdapter(getChildFragmentManager());
        moviesViewPager.setAdapter(pagerAdapter);
    }

    private void initTabLayout() {
        tabLayout.setupWithViewPager(moviesViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (aboutDialog != null) {
            aboutDialog.setOnDismissListener(null);
            aboutDialog.dismiss();
        }
    }

}
