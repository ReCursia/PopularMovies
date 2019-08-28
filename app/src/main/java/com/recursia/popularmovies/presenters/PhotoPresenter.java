package com.recursia.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.views.PhotoContract;

@InjectViewState
public class PhotoPresenter extends MvpPresenter<PhotoContract> {
    private final String imagePath;

    public PhotoPresenter(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setImageData(imagePath);
    }

}
