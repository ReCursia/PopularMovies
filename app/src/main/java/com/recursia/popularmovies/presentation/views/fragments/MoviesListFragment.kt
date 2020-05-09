package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.presentation.presenters.MoviesListPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.contracts.MoviesListContract
import com.recursia.popularmovies.utils.NetworkUtils.TOP_RATED
import com.recursia.popularmovies.utils.TagUtils.FRAGMENT_MOVIES
import com.recursia.popularmovies.utils.discover.PopularityDiscoverStrategy
import com.recursia.popularmovies.utils.discover.TopRatedDiscoverStrategy

class MoviesListFragment : MvpAppCompatFragment(), MoviesListContract {
    @BindView(R.id.recyclerViewPosters)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.swipeIndicator)
    lateinit var swipeIndicator: SwipeRefreshLayout

    @InjectPresenter
    lateinit var presenter: MoviesListPresenter
    private lateinit var moviesAdapter: MoviesAdapter

    @ProvidePresenter
    fun providePresenter(): MoviesListPresenter { // Strategy
        val sortStrategy = arguments!!.getString(FRAGMENT_MOVIES)
        val strategy = if (sortStrategy == TOP_RATED) TopRatedDiscoverStrategy() else PopularityDiscoverStrategy()
        val app = TheApplication.getInstance().appComponent
        return MoviesListPresenter(app.moviesListInteractor, strategy, app.router)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
        initAdapter()
        initRecyclerView()
        initSwipeToRefreshLayout()
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(activity!!, IS_RECOMMENDATION_MOVIES)
        moviesAdapter.setClickListener {
            presenter.onMovieClicked(it)
        }
        recyclerView.adapter = moviesAdapter
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
        recyclerView.setHasFixedSize(true) // items are same height
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val isBottomReached = !recyclerView.canScrollVertically(DIRECTION_UP)
                if (isBottomReached) {
                    presenter.bottomIsReached()
                }
            }
        })
    }

    private fun initSwipeToRefreshLayout() {
        swipeIndicator.setOnRefreshListener { presenter.onSwipeRefreshed() }
        swipeIndicator.setColorSchemeColors(resources.getColor(R.color.colorAccent))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun showLoading() {
        swipeIndicator.isRefreshing = true
    }

    override fun hideLoading() {
        if (swipeIndicator.isRefreshing) {
            swipeIndicator.isRefreshing = false
        }
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun setMovies(movies: List<Movie>) {
        moviesAdapter.setMovies(movies as MutableList<Movie>)
    }

    override fun addMovies(movies: List<Movie>) {
        moviesAdapter.addMovies(movies)
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val DIRECTION_UP = 1
        private const val IS_RECOMMENDATION_MOVIES = false
    }
}
