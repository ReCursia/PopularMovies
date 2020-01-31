package com.recursia.popularmovies.presentation.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.recursia.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenFragment extends MvpAppCompatFragment {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView navigationView;

    public static MainScreenFragment getInstance() {
        return new MainScreenFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFirstScreen();
        initTabChangeListener();
    }

    private void initTabChangeListener() {
        //TODO make it with presenter
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            switch (id) {
                case R.id.navigation_popular:
                    openPopularScreen();
                    break;
                case R.id.navigation_search:
                    openSearchScreen();
                    break;
                case R.id.navigation_favorite:
                    openFavoriteScreen();
                    break;
                case R.id.navigation_account:
                    //TODO implement account screen
                    break;
            }
            return true;
        });
    }

    private void openFavoriteScreen() {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FavoriteScreenFragment.getInstance())
                .commit();
    }

    private void openSearchScreen() {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, SearchScreenFragment.getInstance())
                .commit();
    }

    private void openPopularScreen() {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, PopularScreenFragment.getInstance())
                .commit();
    }

    private void initFirstScreen() {
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, PopularScreenFragment.getInstance())
                .commit();
    }
}
