package com.example.popularmovies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.popularmovies.models.pojo.SectionItem;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SectionContract extends MvpView {
    void setSectionData(SectionItem item);
}
