package com.example.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class MovieViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private LifecycleOwner owner;

    public MovieViewModelFactory(Application application, LifecycleOwner owner) {
        this.application = application;
        this.owner = owner;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModelImpl(application, owner);
    }
}
