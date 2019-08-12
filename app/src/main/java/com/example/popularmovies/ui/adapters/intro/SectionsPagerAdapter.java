package com.example.popularmovies.ui.adapters.intro;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.popularmovies.R;
import com.example.popularmovies.models.pojo.SectionItem;
import com.example.popularmovies.ui.fragments.SectionFragment;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private List<SectionItem> sectionItemList;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        initItems();
    }

    private void initItems() {
        sectionItemList = new ArrayList<>();
        //TODO я не уверен, должны ли адаптеры создавать эти данные или получать из активити, которое эти данные создает (скорее второе)
        sectionItemList.add(new SectionItem(R.drawable.zune, "SAVE DATA"));
        sectionItemList.add(new SectionItem(R.drawable.movie, "WATCH DETAIL MOVIE INFORMATION"));
        sectionItemList.add(new SectionItem(R.drawable.youtube, "WATCH TOP RATED AND POPULAR MOVIES"));
    }

    @Override
    public Fragment getItem(int i) {
        return getInstance(i);
    }

    private Fragment getInstance(int index) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", sectionItemList.get(index));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return sectionItemList.size();
    }

}
