package com.example.popularmovies.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.popularmovies.R;
import com.example.popularmovies.presenters.PhotoPresenter;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.utils.TagUtils;
import com.example.popularmovies.views.PhotoContract;
import com.ortiz.touchview.TouchImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends MvpAppCompatActivity implements PhotoContract {
    @BindView(R.id.backdropImage)
    TouchImageView backdropImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @InjectPresenter
    PhotoPresenter presenter;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @ProvidePresenter
    PhotoPresenter providePresenter() {
        Intent intent = getIntent();
        return new PhotoPresenter(intent.getStringExtra(TagUtils.IMAGE_PATH));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        //add back arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.photo_detail));
        }
    }

    @Override
    public void setImageData(String imagePath) {
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
