package com.recursia.popularmovies.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.recursia.popularmovies.ui.fragments.MoviesFragment;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.utils.TagUtils;

public class MoviesPagerAdapter extends FragmentStatePagerAdapter {
    private static final int TAB_COUNT = 2;

    private final String[] argsList = {NetworkUtils.POPULARITY, NetworkUtils.TOP_RATED};
    private final String[] titles = {"POPULAR", "TOP RATED"};

    public MoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

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
        //Passing args
        Bundle args = new Bundle();
        args.putString(TagUtils.FRAGMENT_MOVIES, argsList[index]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

}
