package com.example.popularmovies.ui.adapters.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.ui.adapters.OnItemClickListener;
import com.example.popularmovies.ui.fragments.MoviesFragment;
import com.example.popularmovies.utils.NetworkUtils;

public class MoviesPagerAdapter extends FragmentStatePagerAdapter {
    private final int TAB_COUNT = 2;

    private final String[] argsList = {NetworkUtils.POPULARITY, NetworkUtils.TOP_RATED};
    private final String[] titles = {"POPULAR", "TOP RATED"};
    private OnItemClickListener<Movie> listener;

    public MoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setClickListener(OnItemClickListener<Movie> newListener) {
        this.listener = newListener;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int i) {
        return getInstance(i);
    }

    private Fragment getInstance(int index) {
        MoviesFragment fragment = new MoviesFragment();
        //Setting listeners, so we can provide clicked movie to main activity
        fragment.setOnMovieClickedListener(item -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
        Bundle args = new Bundle();
        args.putString("sortBy", argsList[index]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

}
