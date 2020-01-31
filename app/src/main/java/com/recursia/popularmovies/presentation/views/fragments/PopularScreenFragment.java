package com.recursia.popularmovies.presentation.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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

    public static PopularScreenFragment getInstance() {
        return new PopularScreenFragment();
    }

    @ProvidePresenter
    PopularScreenPresenter providePresenter() {
        AppComponent app = TheApplication.getInstance().getAppComponent();
        return new PopularScreenPresenter(app.getRouter());
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
        toolbar.setTitle(R.string.app_name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.black));
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
    }

}
