package com.recursia.popularmovies.presentation.views.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.recursia.popularmovies.presentation.models.SectionItem;
import com.recursia.popularmovies.presentation.views.fragments.SectionScreenFragment;
import com.recursia.popularmovies.utils.TagUtils;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private List<SectionItem> sectionItemList;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        sectionItemList = new ArrayList<>();
    }

    public void setSections(List<SectionItem> sectionItemList) {
        this.sectionItemList = sectionItemList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return getInstance(i);
    }

    private Fragment getInstance(int index) {
        SectionScreenFragment fragment = new SectionScreenFragment();
        //Passing args
        Bundle args = new Bundle();
        args.putSerializable(TagUtils.FRAGMENT_INTRO, sectionItemList.get(index));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return sectionItemList.size();
    }

}
