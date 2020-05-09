package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.recursia.popularmovies.presentation.presenters.FavoriteScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.contracts.FavoriteScreenContract

class FavoriteScreenFragment : MvpAppCompatFragment(), FavoriteScreenContract {
    @BindView(R.id.favoriteRecycleView)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.noFavoriteMoviesScreen)
    lateinit var noFavoriteMoviesScreen: View

    @InjectPresenter
    lateinit var presenter: FavoriteScreenPresenter
    private lateinit var moviesAdapter: MoviesAdapter

    @ProvidePresenter
    fun providePresenter(): FavoriteScreenPresenter {
        val app = TheApplication.getInstance().appComponent
        return FavoriteScreenPresenter(app.favoriteScreenInteractor, app.router)
    }

    override fun showNoFavoriteScreen() {
        noFavoriteMoviesScreen.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideNoFavoriteScreen() {
        noFavoriteMoviesScreen.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun setMovies(movies: List<Movie>) {
        moviesAdapter.setMovies(movies as MutableList<Movie>)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        initAdapter()
    }

    private fun initToolbar() {
        toolbar.setBackgroundColor(resources.getColor(R.color.black))
        toolbar.title = getString(R.string.favorite_item)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerView.setHasFixedSize(true) // items are same height
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(context!!, IS_RECOMMENDATION_MOVIES)
        moviesAdapter.setClickListener {
            presenter.onItemClicked(it)
        }
        recyclerView.adapter = moviesAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val IS_RECOMMENDATION_MOVIES = false

        val instance: FavoriteScreenFragment
            get() = FavoriteScreenFragment()
    }
}
