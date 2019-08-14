package com.example.popularmovies.ui.adapters.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.popularmovies.models.pojo.SectionItem;
import com.example.popularmovies.ui.fragments.SectionFragment;
import com.example.popularmovies.utils.TagUtils;

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
        SectionFragment fragment = new SectionFragment();
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
