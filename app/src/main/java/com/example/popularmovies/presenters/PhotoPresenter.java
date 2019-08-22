package com.example.popularmovies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.popularmovies.views.PhotoContract;

@InjectViewState
public class PhotoPresenter extends MvpPresenter<PhotoContract> {
    private String imagePath;

    public PhotoPresenter(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setImageData(imagePath);
    }

}
