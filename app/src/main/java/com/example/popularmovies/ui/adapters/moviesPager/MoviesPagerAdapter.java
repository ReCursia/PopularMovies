package com.example.popularmovies.ui.adapters.moviesPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.ui.adapters.OnItemClickListener;
import com.example.popularmovies.ui.fragments.MoviesFragment;

import java.util.List;

public class MoviesPagerAdapter extends FragmentStatePagerAdapter {
    private final int TAB_COUNT = 2;

    private final String[] titles = {"POPULAR", "TOP RATED"};
    private final List<String> argsList;
    private OnItemClickListener<Movie> listener;

    public MoviesPagerAdapter(FragmentManager fm, List<String> argsList) {
        super(fm);
        this.argsList = argsList;
    }

    public void setClickListener(OnItemClickListener<Movie> newListener) {
        this.listener = newListener;
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
        Log.i("FRAGMENT_LISTENER", "adding listener to fragment with index: " + index);
        fragment.setOnMovieClickedListener(item -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
        Bundle args = new Bundle();
        args.putString("sortBy", argsList.get(index));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

}
