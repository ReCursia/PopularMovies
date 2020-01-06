package com.recursia.popularmovies.presentation.views.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ortiz.touchview.TouchImageView;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.TheApplication;
import com.recursia.popularmovies.di.AppComponent;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.utils.TagUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class PhotoScreenFragment extends MvpAppCompatFragment {
    @BindView(R.id.backdropImage)
    TouchImageView backdropImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Router router;

    public static PhotoScreenFragment getInstance(String imagePath) {
        PhotoScreenFragment fragment = new PhotoScreenFragment();
        Bundle arguments = new Bundle();
        arguments.putString(TagUtils.IMAGE_PATH, imagePath);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppComponent appComponent = TheApplication
                .getInstance()
                .getAppComponent();
        router = appComponent.getRouter();

        initToolbar();
        setImageData(getArguments().getString(TagUtils.IMAGE_PATH));
    }

    private void initToolbar() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.black));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> router.exit());
        toolbar.setTitle(getString(R.string.photo_detail));
    }

    private void setImageData(String imagePath) {
        //Image
        Glide.with(this)
                .asBitmap()
                .load(NetworkUtils.getOriginalPosterUrl(imagePath))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        backdropImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

}
