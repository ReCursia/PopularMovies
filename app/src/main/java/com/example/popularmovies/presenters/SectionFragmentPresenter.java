package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.models.pojo.SectionItem;
import com.example.popularmovies.views.SectionContract;

@InjectViewState
public class SectionFragmentPresenter extends MvpPresenter<SectionContract> {
    private SectionItem item;

    public SectionFragmentPresenter(SectionItem item) {
        this.item = item;
        initData();
    }

    private void initData() {
        getViewState().setData(item);
    }

}
