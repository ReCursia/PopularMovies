package com.example.popularmovies.screens.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.example.popularmovies.R;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.viewmodel.MovieViewModelFactory;
import com.example.popularmovies.viewmodel.MovieViewModelImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends MvpAppCompatActivity implements DetailContract {
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.ratingTextView)
    TextView ratingTextView;
    @BindView(R.id.originalTitleTextView)
    TextView originalTitleTextView;
    @BindView(R.id.releaseDateTextView)
    TextView releaseDateTextView;
    @BindView(R.id.posterImage)
    ImageView posterImage;
    @BindView(R.id.favoriteIcon)
    ImageView favoriteIcon;
    @InjectPresenter
    DetailPresenter presenter;

    @OnClick(R.id.favoriteIcon)
    public void onFavoriteIconClicked() {
        presenter.onFavoriteIconClicked();
    }

    @ProvidePresenter
    DetailPresenter providePresenter() {
        MovieViewModelFactory factory = new MovieViewModelFactory(getApplication(), this);
        Intent intent = getIntent();
        return new DetailPresenter(ViewModelProviders.of(this, factory).get(MovieViewModelImpl.class), intent.getIntExtra("id", 920));
    }

    @Override
    public void setFavoriteIconOn() {
        favoriteIcon.setImageDrawable(getDrawable(R.drawable.favorite_icon_on));
    }

    @Override
    public void showMovieAddedMessage() {
        Toast.makeText(this, getString(R.string.added_favorite), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMovieRemovedMessage() {
        Toast.makeText(this, getString(R.string.removed_favorite), Toast.LENGTH_LONG).show();
    }

    @Override
    public void setFavoriteIconOff() {
        favoriteIcon.setImageDrawable(getDrawable(R.drawable.favorite_icon_off));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void setMovieDetail(Movie movie) {
        getSupportActionBar().setTitle(movie.getTitle());
        titleTextView.setText(movie.getTitle());
        originalTitleTextView.setText(movie.getOriginalTitle());
        releaseDateTextView.setText(movie.getReleaseDate());
        descriptionTextView.setText(movie.getOverview());
        ratingTextView.setText(movie.getVoteAverage() + "");
        //Image
        Glide.with(this)
                .load(NetworkUtils.BASE_POSTER_URL + NetworkUtils.BIG_POSTER_SIZE + movie.getPosterPath())
                .into(posterImage);
    }
}
