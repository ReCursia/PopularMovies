package com.example.popularmovies.screens.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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
    private final static int FADE_OUT_DURATION = 100; //ms

    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.ratingTextView)
    TextView ratingTextView;
    @BindView(R.id.originalTitleTextView)
    TextView originalTitleTextView;
    @BindView(R.id.releaseDateTextView)
    TextView releaseDateTextView;
    @BindView(R.id.recycleViewTrailers)
    RecyclerView recyclerView;
    @BindView(R.id.posterImage)
    ImageView posterImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @InjectPresenter
    DetailPresenter presenter;

    private TrailersAdapter adapter;
    private Menu menu;

    @Override
    public void setTrailers(List<Trailer> trailers) {
        adapter.setTrailers(trailers);
    }

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
        if (menu != null) {
            menu.findItem(R.id.favoriteIcon).setIcon(getDrawable(R.drawable.favorite_icon_on));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favoriteIcon) {
            presenter.onFavoriteIconClicked();
        }
        return super.onOptionsItemSelected(item);
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
        if (menu != null) {
            menu.findItem(R.id.favoriteIcon).setIcon(getDrawable(R.drawable.favorite_icon_off));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        this.menu = menu;
        presenter.menuIsInflated();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initRecyclerView();
        initAdapter();
        initToolbar();
        initCollapsingToolbarLayout();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        //add back arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initCollapsingToolbarLayout() {
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void openTrailerUrl(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.TRAILER_BASE_URL + adapter.getItem(position).getKey()));
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
        collapsingToolbarLayout.setTitle(movie.getTitle());
        originalTitleTextView.setText(movie.getOriginalTitle());
        releaseDateTextView.setText(movie.getReleaseDate());
        descriptionTextView.setText(movie.getOverview());
        ratingTextView.setText(Double.toString(movie.getVoteAverage()));
        //Image
        Glide.with(this)
                .load(NetworkUtils.BASE_POSTER_URL + NetworkUtils.BIG_POSTER_SIZE + movie.getPosterPath())
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(posterImage);
    }
}
