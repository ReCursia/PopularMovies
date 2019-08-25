package com.recursia.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.models.pojo.SectionItem;
import com.recursia.popularmovies.views.SectionContract;

@InjectViewState
public class SectionFragmentPresenter extends MvpPresenter<SectionContract> {
    private SectionItem item;

    public SectionFragmentPresenter(SectionItem item) {
        this.item = item;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initData();
    }

    private void initData() {
        getViewState().setSectionData(item);
    }

}
