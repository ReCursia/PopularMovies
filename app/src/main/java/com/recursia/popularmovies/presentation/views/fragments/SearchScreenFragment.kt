package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
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
import com.recursia.popularmovies.presentation.presenters.SearchScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.adapters.OnItemClickListener
import com.recursia.popularmovies.presentation.views.contracts.SearchScreenContract

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
        toolbar.setBackgroundColor(resources.getColor(R.color.black))
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { v: View? -> presenter.onBackPressed() }
        toolbar.inflateMenu(R.menu.search_menu)
        val searchItem = toolbar.menu.findItem(R.id.searchItem)
        val searchView = searchItem.actionView as SearchView
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
        searchRecyclerView.setHasFixedSize(true)
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(context!!, false)
        moviesAdapter.setClickListener(object : OnItemClickListener<Movie> {
            override fun onItemClick(movie: Movie) {
                presenter.onItemClicked(movie)
            }
        })
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
        val instance: SearchScreenFragment
            get() = SearchScreenFragment()
    }
}