package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category
import com.recursia.popularmovies.presentation.presenters.MainScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.adapters.common.ItemType
import com.recursia.popularmovies.presentation.views.adapters.common.MovieLargeItemType
import com.recursia.popularmovies.presentation.views.adapters.common.MovieMediumItemType
import com.recursia.popularmovies.presentation.views.contracts.MainScreenContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import com.recursia.popularmovies.utils.intro.PreferencesImpl
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MainScreenFragment : MvpAppCompatFragment(), MainScreenContract {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.recycler_view_popular_now)
    lateinit var recyclerViewPopularNow: RecyclerView

    @BindView(R.id.recycler_view_top_rated)
    lateinit var recyclerViewTopRated: RecyclerView

    @BindView(R.id.recycler_view_upcoming)
    lateinit var recyclerViewUpcoming: RecyclerView

    @BindView(R.id.recycler_view_now_playing)
    lateinit var recyclerViewNowPlaying: RecyclerView

    @InjectPresenter
    lateinit var presenter: MainScreenPresenter

    @ProvidePresenter
    internal fun providePresenter(): MainScreenPresenter {
        val app = TheApplication.getInstance().appComponent
        return MainScreenPresenter(app!!.mainScreenInteractor, PreferencesImpl(context!!), app.router)
    }

    private val categoryMap = mutableMapOf<Category, MoviesAdapter>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun setCategoryMovies(movies: List<Movie>, category: Category) {
        categoryMap[category]?.setMovies(movies as MutableList<Movie>)
    }

    override fun addCategoryMovies(movies: List<Movie>, category: Category) {
        categoryMap[category]?.addMovies(movies as MutableList)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar
        initToolbar()

        // Recycler view
        initRecyclerViewsAndAdapters()
    }

    private fun initRecyclerViewsAndAdapters() {
        for (category in Category.values()) {
            val adapter = MoviesAdapter(context!!, getItemType(category), category.toString())
            adapter.setOnClickListener {
                presenter.onMovieClicked(it)
            }
            categoryMap[category] = adapter
            setRecyclerViewAdapter(category, adapter)
        }
    }

    private fun getItemType(category: Category): ItemType {
        if (category == Category.POPULAR) {
            return MovieLargeItemType()
        }
        return MovieMediumItemType()
    }

    private fun setRecyclerViewAdapter(category: Category, adapter: MoviesAdapter) {
        when (category) {
            Category.TOP_RATED -> initRecyclerView(adapter, category, recyclerViewTopRated)
            Category.NOW_PLAYING -> initRecyclerView(adapter, category, recyclerViewNowPlaying)
            Category.POPULAR -> initRecyclerView(adapter, category, recyclerViewPopularNow)
            Category.UPCOMING -> initRecyclerView(adapter, category, recyclerViewUpcoming)
        }
    }

    private fun initRecyclerView(adapter: MoviesAdapter, category: Category, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
                MarginItemDecoration(context!!, 7, 7, 0, 0)
        )
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val isRightReached = !recyclerView.canScrollHorizontally(DIRECTION_RIGHT)
                if (isRightReached) {
                    presenter.rightIsReached(category)
                }
            }
        })
        recyclerView.adapter = adapter
    }

    private fun initToolbar() {
        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        toolbar.setNavigationIcon(R.drawable.ic_account_circle)
        toolbar.setNavigationOnClickListener { presenter.onAccountClicked() }
        toolbar.inflateMenu(R.menu.search_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.search_item) {
                presenter.onSearchItemClicked()
                return@setOnMenuItemClickListener true
            }
            false
        }
    }

    companion object {
        fun getInstance() = MainScreenFragment()
        private const val DIRECTION_RIGHT = 1
    }
}
