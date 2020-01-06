package com.recursia.popularmovies.presentation.ui.activities;

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
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.TheApplication;
import com.recursia.popularmovies.di.AppComponent;
import com.recursia.popularmovies.domain.models.Cast;
import com.recursia.popularmovies.domain.models.Genre;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.domain.models.Trailer;
import com.recursia.popularmovies.presentation.presenters.DetailScreenPresenter;
import com.recursia.popularmovies.presentation.ui.adapters.CastAdapter;
import com.recursia.popularmovies.presentation.ui.adapters.MoviesAdapter;
import com.recursia.popularmovies.presentation.ui.adapters.TrailersAdapter;
import com.recursia.popularmovies.presentation.ui.decorations.MarginItemDecoration;
import com.recursia.popularmovies.presentation.ui.navigation.Navigator;
import com.recursia.popularmovies.presentation.views.DetailScreenContract;
import com.recursia.popularmovies.utils.DateUtils;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.utils.TagUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailScreenActivity extends MvpAppCompatActivity implements DetailScreenContract {
    private final static int FADE_OUT_DURATION = 100; //ms
    private final static boolean IS_RECOMMENDATION_MOVIES = true;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.ratingTextView)
    TextView ratingTextView;
    @BindView(R.id.originalTitleTextView)
    TextView originalTitleTextView;
    @BindView(R.id.favoriteIcon)
    FloatingActionButton favoriteIcon;
    @BindView(R.id.releaseDateTextView)
    TextView releaseDateTextView;
    @BindView(R.id.recycleViewTrailers)
    RecyclerView recyclerViewTrailers;
    @BindView(R.id.backdropImage)
    ImageView backdropImage;
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
    @BindView(R.id.recyclerViewMovieRecommendations)
    RecyclerView recyclerViewMovieRecommendations;
    @BindView(R.id.movieRecommendationCardView)
    CardView movieRecommendationCardView;
    @InjectPresenter
    DetailScreenPresenter presenter;
    private TrailersAdapter trailersAdapter;
    private CastAdapter castAdapter;
    private MoviesAdapter moviesAdapter;
    private Movie movie;

    @Override
    public void showFavoriteIcon() {
        favoriteIcon.show();
    }

    @Override
    public void hideFavoriteIcon() {
        favoriteIcon.hide();
    }

    @Override
    public void openPhotoDetail(String imagePath) {
        Navigator.openPhotoDetail(this, imagePath);
    }

    @OnClick(R.id.favoriteIcon)
    public void onFavoriteIconClicked() {
        presenter.onFavoriteIconClicked(movie);
    }

    @OnClick(R.id.backdropImage)
    public void onBackdropImageClicked() {
        presenter.onBackdropImageClicked(movie);
    }

    @Override
    public void setRecommendationMovies(List<Movie> movies) {
        moviesAdapter.setMovies(movies);
    }

    @Override
    public void hideRecommendationMovies() {
        movieRecommendationCardView.setVisibility(View.GONE);
    }

    @Override
    public void showRecommendationMovies() {
        movieRecommendationCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void openDetailScreen(Movie movie) {
        Navigator.openDetailScreen(this, movie.getId());
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void setGenres(List<Genre> genres) {
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
    DetailScreenPresenter providePresenter() {
        Intent intent = getIntent();
        int movieId = intent.getIntExtra(TagUtils.MOVIE_ID, 0);
        AppComponent app = TheApplication.getInstance().getAppComponent();
        return new DetailScreenPresenter(app.getDetailScreenInteractor(), movieId);
    }

    @Override
    public void setFavoriteIconOn() {
        favoriteIcon.setImageDrawable(getDrawable(R.drawable.ic_favorite_on));
        favoriteIcon.setImageMatrix(new Matrix()); //trick
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shareItem) {
            presenter.onShareIconClicked(movie);
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
        detailCardView.setVisibility(View.GONE);
        descriptionCardView.setVisibility(View.GONE);
        recyclerViewTrailers.setVisibility(View.GONE);
        castCardView.setVisibility(View.GONE);
    }

    @Override
    public void showMovieDetail() {
        detailCardView.setVisibility(View.VISIBLE);
        descriptionCardView.setVisibility(View.VISIBLE);
        recyclerViewTrailers.setVisibility(View.VISIBLE);
        castCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFavoriteIconOff() {
        favoriteIcon.setImageDrawable(getDrawable(R.drawable.ic_favorite_off));
        favoriteIcon.setImageMatrix(new Matrix()); //trick
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
        recyclerViewTrailers.setHasFixedSize(true);
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
        recyclerViewCast.setHasFixedSize(true);
        recyclerViewCast.addItemDecoration(
                new MarginItemDecoration(this, 20, 0, 0, 0));
    }

    private void initCreditsAdapter() {
        castAdapter = new CastAdapter(this);
        recyclerViewCast.setAdapter(castAdapter);
    }

    private void initMovieRecommendationRecyclerView() {
        recyclerViewMovieRecommendations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMovieRecommendations.setHasFixedSize(true);
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
    public void openTrailerUrl(Trailer trailer) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.TRAILER_BASE_URL + trailer.getKey()));
        startActivity(browserIntent);
    }

    @Override
    public void setMovieDetail(Movie movie) {
        this.movie = movie;
        //Trailers
        List<Trailer> trailers = movie.getTrailers();
        if (!trailers.isEmpty()) {
            trailersAdapter.setTrailers(trailers);
        }
        //Genres
        List<Genre> genres = movie.getGenres();
        if (!genres.isEmpty()) {
            setGenres(genres);
        }
        //Cast
        List<Cast> casts = movie.getCasts();
        if (!casts.isEmpty()) {
            castAdapter.setCast(casts);
        }
        //Trailers
        collapsingToolbarLayout.setTitle(movie.getTitle());
        originalTitleTextView.setText(movie.getOriginalTitle());
        releaseDateTextView.setText(DateUtils.formatDate(movie.getReleaseDate()));
        descriptionTextView.setText(movie.getOverview());
        ratingTextView.setText(Double.toString(movie.getVoteAverage()));
        //Image
        Glide.with(this)
                .load(NetworkUtils.getBigPosterUrl(movie.getBackdropPath()))
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(backdropImage);
    }

}
