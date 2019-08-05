package com.example.popularmovies.screens.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.adapters.trailers.TrailersAdapter;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.pojo.Trailer;
import com.example.popularmovies.repository.MoviesService;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpAppCompatActivity implements DetailContract {
    /*
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
    */
    @BindView(R.id.recyclerViewTrailers)
    RecyclerView recyclerView;
    /*
    @BindView(R.id.favoriteIcon)
    ImageView favoriteIcon;
    */
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @InjectPresenter
    DetailPresenter presenter;

    private TrailersAdapter adapter;

    @Override
    public void setTrailers(List<Trailer> trailers) {
        adapter.setTrailers(trailers);
    }
    /*
    @OnClick(R.id.favoriteIcon)
    public void onFavoriteIconClicked() {
        presenter.onFavoriteIconClicked();
    }
    */

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @ProvidePresenter
    DetailPresenter providePresenter() {
        Intent intent = getIntent();
        return new DetailPresenter(MoviesService.getInstance().getMoviesApi(), intent.getIntExtra("id", 0));
    }

    @Override
    public void setFavoriteIconOn() {
        //favoriteIcon.setImageDrawable(getDrawable(R.drawable.favorite_icon_on));
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
        //favoriteIcon.setImageDrawable(getDrawable(R.drawable.favorite_icon_off));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initRecyclerView();
        initAdapter();
        initCollapsingToolbarLayout();
        setSupportActionBar(toolbar);
    }

    private void initCollapsingToolbarLayout() {
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void openTrailerUrl(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.TRAILER_BASE_URL + adapter.getItem(position).getSite()));
        //TODO я думаю надо в презентер передавать TRAILER и уже там решать что делать... А не так как сейчас (аля логика в UI)
        startActivity(browserIntent);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initAdapter() {
        adapter = new TrailersAdapter();
        adapter.setClickListener(position -> presenter.onTrailerPlayButtonClicked(position));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setMovieDetail(Movie movie) {
        getSupportActionBar().setTitle(movie.getTitle());
        //titleTextView.setText(movie.getTitle());
        //originalTitleTextView.setText(movie.getOriginalTitle());
        //releaseDateTextView.setText(movie.getReleaseDate());
        //descriptionTextView.setText(movie.getOverview());
        //ratingTextView.setText(Double.toString(movie.getVoteAverage()));
        //Image
        /*
        Glide.with(this)
                .load(NetworkUtils.BASE_POSTER_URL + NetworkUtils.BIG_POSTER_SIZE + movie.getPosterPath())
                .into(posterImage);
                */
    }
}
