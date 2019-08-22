package com.example.popularmovies.ui.activities;

import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import com.example.popularmovies.models.pojo.MovieExtra;
import com.example.popularmovies.models.pojo.Trailer;
import com.example.popularmovies.presenters.DetailPresenter;
import com.example.popularmovies.ui.adapters.credits.CreditsAdapter;
import com.example.popularmovies.ui.adapters.movies.MoviesAdapter;
import com.example.popularmovies.ui.adapters.trailers.TrailersAdapter;
import com.example.popularmovies.ui.decorations.MarginItemDecoration;
import com.example.popularmovies.utils.DateUtils;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.utils.TagUtils;
import com.example.popularmovies.views.DetailContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends MvpAppCompatActivity implements DetailContract {
    private final static int FADE_OUT_DURATION = 100; //ms
    private final static boolean IS_RECOMMENDATION_MOVIES = true;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.ratingTextView)
    TextView ratingTextView;
    @BindView(R.id.originalTitleTextView)
    TextView originalTitleTextView;
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
    @BindView(R.id.descriptionCardView)
    CardView descriptionCardView;
    @BindView(R.id.detailCardView)
    CardView detailCardView;
    @BindView(R.id.favoriteIcon)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.recyclerViewMovieRecommendations)
    RecyclerView recyclerViewMovieRecommendations;
    @BindView(R.id.movieRecommendationCardView)
    CardView movieRecommendationCardView;
    @InjectPresenter
    DetailPresenter presenter;
    private TrailersAdapter trailersAdapter;
    private CreditsAdapter creditsAdapter;
    private MoviesAdapter moviesAdapter;

    @OnClick(R.id.favoriteIcon)
    public void onFavoriteIconClicked() {
        presenter.onFavoriteIconClicked();
    }

    @Override
    public void setCast(List<Cast> cast) {
        creditsAdapter.setCast(cast);
    }

    @Override
    public void showCast() {
        castCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setRecommendationMovies(List<Movie> movies) {
        moviesAdapter.setMovies(movies);
    }

    @Override
    public void showMovies() {
        movieRecommendationCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMovies() {
        movieRecommendationCardView.setVisibility(View.GONE);
    }

    @Override
    public void openDetailScreen(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", movie.getId());
        startActivity(intent);
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
            Chip chip = (Chip) layoutInflater.inflate(R.layout.genre_chip, genresGroup, false);
            chip.setText(genre.getName());
            genresGroup.addView(chip);
        }
    }

    @ProvidePresenter
    DetailPresenter providePresenter() {
        Intent intent = getIntent();
        return new DetailPresenter(MoviesService.getInstance().getMoviesApi(),
                MovieDatabase.getInstance(this).movieDao(),
                new MovieExtra(),
                intent.getIntExtra(TagUtils.MOVIE_ID, 0));
    }

    @Override
    public void setFavoriteIconOn() {
        floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_on));
        floatingActionButton.setImageMatrix(new Matrix()); //trick, bug is not fixed yet
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shareItem) {
            presenter.onShareIconClicked();
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
    public void shareMovie(Movie movie) {
        String shareMessage = getShareMessage(movie);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        intent.setType("text/plain");
        Intent chosenIntent = Intent.createChooser(intent, getString(R.string.share_to));
        startActivity(chosenIntent);
    }

    private String getShareMessage(Movie movie) {
        return String.format(getString(R.string.share_message), movie.getTitle(), NetworkUtils.getBigPosterUrl(movie.getPosterPath()));
    }

    @Override
    public void hideMovieDetail() {
        //Detail cardView
        detailCardView.setVisibility(View.GONE);
        descriptionCardView.setVisibility(View.GONE);
    }

    @Override
    public void showMovieDetail() {
        detailCardView.setVisibility(View.VISIBLE);
        descriptionCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFavoriteIconOff() {
        floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_off));
        floatingActionButton.setImageMatrix(new Matrix()); //trick, bug is not fixed yet
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        //Trailers
        initTrailerRecyclerView();
        initTrailersAdapter();
        //Cast
        initCastRecyclerView();
        initCreditsAdapter();
        //Movie recommendation
        initMovieRecommendationRecyclerView();
        initMovieRecommendationAdapter();

        initToolbar();
        initCollapsingToolbarLayout();
    }

    private void initTrailerRecyclerView() {
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTrailers.addItemDecoration(
                new MarginItemDecoration(this, 7, 7, 0, 0));
    }

    private void initTrailersAdapter() {
        trailersAdapter = new TrailersAdapter(this);
        trailersAdapter.setClickListener(item -> presenter.onTrailerPlayButtonClicked(item));
        recyclerViewTrailers.setAdapter(trailersAdapter);
    }

    private void initCastRecyclerView() {
        recyclerViewCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCast.addItemDecoration(
                new MarginItemDecoration(this, 20, 0, 0, 0));
    }

    private void initCreditsAdapter() {
        creditsAdapter = new CreditsAdapter(this);
        recyclerViewCast.setAdapter(creditsAdapter);
    }

    private void initMovieRecommendationRecyclerView() {
        recyclerViewMovieRecommendations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMovieRecommendations.addItemDecoration(
                new MarginItemDecoration(this, 7, 7, 0, 0));
    }

    private void initMovieRecommendationAdapter() {
        moviesAdapter = new MoviesAdapter(this, IS_RECOMMENDATION_MOVIES);
        moviesAdapter.setClickListener(item -> presenter.onMovieClicked(item));
        recyclerViewMovieRecommendations.setAdapter(moviesAdapter);
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
        originalTitleTextView.setText(movie.getOriginalTitle());
        releaseDateTextView.setText(DateUtils.formatDate(movie.getReleaseDate()));
        descriptionTextView.setText(movie.getOverview());
        ratingTextView.setText(Double.toString(movie.getVoteAverage()));
        //Image
        Glide.with(this)
                .load(NetworkUtils.getBigPosterUrl(movie.getBackdropPath()))
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(posterImage);
    }

}
