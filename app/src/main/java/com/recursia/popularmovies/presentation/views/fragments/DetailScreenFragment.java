package com.recursia.popularmovies.presentation.views.fragments;

import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
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
import com.recursia.popularmovies.presentation.views.adapters.CastAdapter;
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter;
import com.recursia.popularmovies.presentation.views.adapters.TrailersAdapter;
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration;
import com.recursia.popularmovies.presentation.views.contracts.DetailScreenContract;
import com.recursia.popularmovies.utils.DateUtils;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.utils.TagUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//TODO return is empty checks back to presenter
public class DetailScreenFragment extends MvpAppCompatFragment implements DetailScreenContract {
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

    public static DetailScreenFragment getInstance(int movieId) {
        DetailScreenFragment fragment = new DetailScreenFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(TagUtils.MOVIE_ID, movieId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void showFavoriteIcon() {
        favoriteIcon.show();
    }

    @Override
    public void hideFavoriteIcon() {
        favoriteIcon.hide();
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
        if (!movies.isEmpty()) {
            moviesAdapter.setMovies(movies);
        }
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
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @ProvidePresenter
    DetailScreenPresenter providePresenter() {
        int movieId = getArguments().getInt(TagUtils.MOVIE_ID);
        AppComponent app = TheApplication.getInstance().getAppComponent();
        return new DetailScreenPresenter(app.getDetailScreenInteractor(), app.getRouter(), movieId);
    }

    @Override
    public void setFavoriteIconOn() {
        favoriteIcon.setImageDrawable(getContext().getDrawable(R.drawable.ic_favorite_on));
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
        Toast.makeText(getContext(), getString(R.string.added_favorite), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMovieRemovedMessage() {
        Toast.makeText(getContext(), getString(R.string.removed_favorite), Toast.LENGTH_LONG).show();
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
        favoriteIcon.setImageDrawable(getContext().getDrawable(R.drawable.ic_favorite_off));
        favoriteIcon.setImageMatrix(new Matrix()); //trick
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTrailers.setHasFixedSize(true);
        recyclerViewTrailers.addItemDecoration(
                new MarginItemDecoration(getContext(), 7, 7, 0, 0));
    }

    private void initTrailersAdapter() {
        trailersAdapter = new TrailersAdapter(getContext());
        trailersAdapter.setClickListener(item -> presenter.onTrailerPlayButtonClicked(item));
        recyclerViewTrailers.setAdapter(trailersAdapter);
    }

    private void initCastRecyclerView() {
        recyclerViewCast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCast.setHasFixedSize(true);
        recyclerViewCast.addItemDecoration(
                new MarginItemDecoration(getContext(), 20, 0, 0, 0));
    }

    private void initCreditsAdapter() {
        castAdapter = new CastAdapter(getContext());
        recyclerViewCast.setAdapter(castAdapter);
    }

    private void initMovieRecommendationRecyclerView() {
        recyclerViewMovieRecommendations.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMovieRecommendations.setHasFixedSize(true);
        recyclerViewMovieRecommendations.addItemDecoration(
                new MarginItemDecoration(getContext(), 7, 7, 0, 0));
    }

    private void initMovieRecommendationAdapter() {
        moviesAdapter = new MoviesAdapter(getContext(), IS_RECOMMENDATION_MOVIES);
        moviesAdapter.setClickListener(item -> presenter.onMovieClicked(item));
        recyclerViewMovieRecommendations.setAdapter(moviesAdapter);
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> presenter.onBackPressed());
        toolbar.inflateMenu(R.menu.detail_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.shareItem) {
                presenter.onShareIconClicked(movie);
                return true;
            }
            return false;
        });
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

    private void setGenres(List<Genre> genres) {
        //Before set genres remove previous one
        genresGroup.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (Genre genre : genres) {
            Chip chip = (Chip) layoutInflater.inflate(R.layout.genre_chip, genresGroup, false);
            chip.setText(genre.getName());
            genresGroup.addView(chip);
        }
    }

}
