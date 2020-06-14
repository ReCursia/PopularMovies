package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.presentation.presenters.SearchScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.adapters.common.MovieStretchItemType
import com.recursia.popularmovies.presentation.views.contracts.SearchScreenContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SearchScreenFragment : MvpAppCompatFragment(), SearchScreenContract {
    @BindView(R.id.searchRecyclerView)
    lateinit var searchRecyclerView: RecyclerView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @InjectPresenter
    lateinit var presenter: SearchScreenPresenter
    private lateinit var moviesAdapter: MoviesAdapter
    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    @ProvidePresenter
    fun providePresenter(): SearchScreenPresenter {
        val app = TheApplication.getInstance().appComponent
        return SearchScreenPresenter(app.searchScreenInteractor, app.router)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        initAdapter()
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.search_movie_title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        toolbar.inflateMenu(R.menu.search_menu)
        val searchItem = toolbar.menu.findItem(R.id.search_item)
        val searchView = searchItem.actionView as SearchView
        searchItem.expandActionView()
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                presenter.onQueryTextChanged(s)
                return false
            }
        })
    }

    private fun initRecyclerView() {
        searchRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        searchRecyclerView.addItemDecoration(
                MarginItemDecoration(context!!, 10, 10, 5, 5)
        )
        searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val isBottomReached = !recyclerView.canScrollVertically(DIRECTION_DOWN)
                if (isBottomReached) {
                    presenter.bottomIsReached()
                }
            }
        })
        searchRecyclerView.setHasFixedSize(true)
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(context!!, MovieStretchItemType())
        moviesAdapter.setOnClickListener {
            presenter.onItemClicked(it)
        }
        searchRecyclerView.adapter = moviesAdapter
    }

    override fun setMovies(movies: List<Movie>) {
        moviesAdapter.setMovies(movies as MutableList<Movie>)
    }

    override fun addMovies(movies: List<Movie>) {
        moviesAdapter.addMovies(movies)
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val DIRECTION_DOWN = 1
        val instance: SearchScreenFragment
            get() = SearchScreenFragment()
    }
}
