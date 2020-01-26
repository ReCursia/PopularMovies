package com.recursia.popularmovies.presentation.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.TheApplication;
import com.recursia.popularmovies.di.AppComponent;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.presenters.MoviesListPresenter;
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter;
import com.recursia.popularmovies.presentation.views.contracts.MoviesListContract;
import com.recursia.popularmovies.utils.NetworkUtils;
import com.recursia.popularmovies.utils.TagUtils;
import com.recursia.popularmovies.utils.discover.DiscoverStrategy;
import com.recursia.popularmovies.utils.discover.PopularityDiscoverStrategy;
import com.recursia.popularmovies.utils.discover.TopRatedDiscoverStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListFragment extends MvpAppCompatFragment implements MoviesListContract {
    private static final int SPAN_COUNT = 2;
    private static final int DIRECTION_UP = 1;
    private static final boolean IS_RECOMMENDATION_MOVIES = false;

    @BindView(R.id.recyclerViewPosters)
    RecyclerView recyclerView;
    @BindView(R.id.swipeIndicator)
    SwipeRefreshLayout swipeIndicator;

    @InjectPresenter
    MoviesListPresenter presenter;

    private MoviesAdapter moviesAdapter;

    @ProvidePresenter
    MoviesListPresenter providePresenter() {
        //Strategy
        String sortStrategy = getArguments().getString(TagUtils.FRAGMENT_MOVIES);
        DiscoverStrategy strategy = (sortStrategy.equals(NetworkUtils.TOP_RATED)) ?
                new TopRatedDiscoverStrategy() :
                new PopularityDiscoverStrategy();
        //Interactor
        AppComponent app = TheApplication.getInstance().getAppComponent();
        return new MoviesListPresenter(app.getMoviesListInteractor(), strategy, app.getRouter());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initAdapter();
        initRecyclerView();
        initSwipeToRefreshLayout();
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(getActivity(), IS_RECOMMENDATION_MOVIES);
        moviesAdapter.setClickListener(item -> presenter.onMovieClicked(item));
        recyclerView.setAdapter(moviesAdapter);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        recyclerView.setHasFixedSize(true); //items are same height
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottomReached = !recyclerView.canScrollVertically(DIRECTION_UP);
                if (isBottomReached) {
                    presenter.bottomIsReached();
                }
            }
        });
    }

    private void initSwipeToRefreshLayout() {
        swipeIndicator.setOnRefreshListener(() -> presenter.onSwipeRefreshed());
        swipeIndicator.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void showLoading() {
        swipeIndicator.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        if (swipeIndicator.isRefreshing()) {
            swipeIndicator.setRefreshing(false);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setMovies(List<Movie> movies) {
        moviesAdapter.setMovies(movies);
    }

    @Override
    public void addMovies(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
    }

}