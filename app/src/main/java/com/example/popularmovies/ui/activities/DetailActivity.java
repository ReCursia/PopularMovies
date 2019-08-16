package com.example.popularmovies.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.popularmovies.R;
import com.example.popularmovies.models.database.MovieDatabase;
import com.example.popularmovies.models.network.MoviesService;
import com.example.popularmovies.models.pojo.Cast;
import com.example.popularmovies.models.pojo.Genre;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.Trailer;
import com.example.popularmovies.models.repository.MovieLocalRepository;
import com.example.popularmovies.models.repository.MovieRepository;
import com.example.popularmovies.presenters.DetailPresenter;
import com.example.popularmovies.ui.adapters.credits.CreditsAdapter;
import com.example.popularmovies.ui.adapters.trailers.TrailersAdapter;
import com.example.popularmovies.utils.DateUtils;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.views.DetailContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpAppCompatActivity implements DetailContract {
    private final static int FADE_OUT_DURATION = 100; //ms
    @BindView(R.id.timeTextView)
    TextView timeTextView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.ratingTextView)
    TextView ratingTextView;
    @BindView(R.id.statusTextView)
    TextView statusTextView;
    @BindView(R.id.releaseDateTextView)
    TextView releaseDateTextView;
    @BindView(R.id.recycleViewTrailers)
    RecyclerView recyclerViewTrailers;
    @BindView(R.id.posterImage)
    ImageView posterImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.genresGroup)
    ChipGroup genresGroup;
    @BindView(R.id.recyclerViewCast)
    RecyclerView recyclerViewCast;
    @BindView(R.id.castCardView)
    CardView castCardView;
    @InjectPresenter
    DetailPresenter presenter;
    private TrailersAdapter trailersAdapter;
    private CreditsAdapter creditsAdapter;
    private Menu menu;

    @Override
    public void setCast(List<Cast> cast) {
        creditsAdapter.setCast(cast);
    }

    @Override
    public void showCast() {
        castCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCast() {
        castCardView.setVisibility(View.GONE);
    }

    @Override
    public void setTrailers(List<Trailer> trailers) {
        trailersAdapter.setTrailers(trailers);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setGenres(List<Genre> genres) {
        //Before set genres remove previous one
        genresGroup.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (Genre genre : genres) {
            Chip chip = (Chip) layoutInflater.inflate(R.layout.genre_chip, null, false);
            chip.setText(genre.getName());
            genresGroup.addView(chip);
        }
    }

    @ProvidePresenter
    DetailPresenter providePresenter() {
        Intent intent = getIntent();
        MovieDatabase db = MovieDatabase.getInstance(this);
        MovieRepository movieRepository = new MovieLocalRepository(db.movieDao(), db.trailerDao(), db.genreDao());
        return new DetailPresenter(MoviesService.getInstance().getMoviesApi(), movieRepository, intent.getIntExtra("id", 0));
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
        initTrailerRecyclerView();
        initCastRecyclerView();
        initTrailersAdapter();
        initCreditsAdapter();
        initToolbar();
        initCollapsingToolbarLayout();
    }

    private void initTrailerRecyclerView() {
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initCastRecyclerView() {
        recyclerViewCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initTrailersAdapter() {
        trailersAdapter = new TrailersAdapter(this);
        trailersAdapter.setClickListener(item -> presenter.onTrailerPlayButtonClicked(item));
        recyclerViewTrailers.setAdapter(trailersAdapter);
    }

    private void initCreditsAdapter() {
        creditsAdapter = new CreditsAdapter(this);
        recyclerViewCast.setAdapter(creditsAdapter);
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
    public void hideTrailers() {
        recyclerViewTrailers.setVisibility(View.GONE);
    }

    @Override
    public void showTrailers() {
        recyclerViewTrailers.setVisibility(View.VISIBLE);
    }

    @Override
    public void openTrailerUrl(Trailer trailer) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.TRAILER_BASE_URL + trailer.getKey()));
        startActivity(browserIntent);
    }

    @Override
    public void setMovieDetail(Movie movie) {
        collapsingToolbarLayout.setTitle(movie.getTitle());
        statusTextView.setText(movie.getStatus());
        timeTextView.setText(DateUtils.formatTime(movie.getRuntime()));
        releaseDateTextView.setText(DateUtils.formatDate(movie.getReleaseDate()));
        descriptionTextView.setText(movie.getOverview());
        ratingTextView.setText(Double.toString(movie.getVoteAverage()));
        //Image
        Glide.with(this)
                .load(NetworkUtils.BASE_IMAGE_URL + NetworkUtils.BIG_POSTER_SIZE + movie.getPosterPath())
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(posterImage);
    }

}
