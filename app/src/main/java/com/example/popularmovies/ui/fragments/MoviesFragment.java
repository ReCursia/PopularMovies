package com.example.popularmovies.ui.fragments;

import android.content.Context;
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
import com.example.popularmovies.R;
import com.example.popularmovies.models.network.MoviesService;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.presenters.MoviesFragmentPresenter;
import com.example.popularmovies.ui.adapters.movies.MoviesAdapter;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.utils.TagUtils;
import com.example.popularmovies.utils.discover.DiscoverStrategy;
import com.example.popularmovies.utils.discover.PopularityDiscoverStrategy;
import com.example.popularmovies.utils.discover.TopRatedDiscoverStrategy;
import com.example.popularmovies.views.MoviesContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends MvpAppCompatFragment implements MoviesContract {
    private static final int SPAN_COUNT = 3;
    private static final int DIRECTION_UP = 1;

    @BindView(R.id.recyclerViewPosters)
    RecyclerView recyclerView;
    @BindView(R.id.swipeIndicator)
    SwipeRefreshLayout swipeIndicator;

    @InjectPresenter
    MoviesFragmentPresenter presenter;

    private MoviesAdapter moviesAdapter;
    private OnFragmentMoviesInteractionListener listener;

    @ProvidePresenter
    MoviesFragmentPresenter providePresenter() {
        String sortStrategy = getArguments().getString(TagUtils.FRAGMENT_MOVIES);

        DiscoverStrategy strategy = (sortStrategy.equals(NetworkUtils.TOP_RATED)) ?
                new TopRatedDiscoverStrategy() :
                new PopularityDiscoverStrategy();

        return new MoviesFragmentPresenter(MoviesService.getInstance().getMoviesApi(), strategy);
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
        moviesAdapter = new MoviesAdapter(getActivity());
        moviesAdapter.setClickListener(item -> presenter.onMovieClicked(item));
        recyclerView.setAdapter(moviesAdapter);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setInteractionListener();
    }

    private void setInteractionListener() {
        //Getting activity and setting listener
        try {
            listener = (OnFragmentMoviesInteractionListener) getActivity();
        } catch (ClassCastException er) {
            throw new ClassCastException(getActivity().toString() + "must implement interface");
        }
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

    @Override
    public void openMovieDetailInformation(Movie movie) {
        if (listener != null) {
            listener.onFragmentMovieClicked(movie);
        }
    }

    public interface OnFragmentMoviesInteractionListener {
        void onFragmentMovieClicked(Movie movie);
    }

}
