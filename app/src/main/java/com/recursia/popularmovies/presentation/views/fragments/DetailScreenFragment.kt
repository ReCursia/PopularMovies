package com.recursia.popularmovies.presentation.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import com.recursia.popularmovies.presentation.presenters.DetailScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviePagerAdapter
import com.recursia.popularmovies.presentation.views.adapters.MovieStatusesAdapter
import com.recursia.popularmovies.presentation.views.contracts.DetailScreenContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import com.recursia.popularmovies.utils.DimensionsUtils
import com.recursia.popularmovies.utils.NetworkUtils
import com.recursia.popularmovies.utils.TagUtils
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout

class DetailScreenFragment : MvpAppCompatFragment(), DetailScreenContract {

    @BindView(R.id.backdrop_image)
    lateinit var backdropImage: ImageView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.collapsing_toolbar)
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    @BindView(R.id.text_view_rating)
    lateinit var textViewRating: TextView

    @BindView(R.id.movie_status_recycler_view)
    lateinit var movieStatusRecyclerView: RecyclerView

    @BindView(R.id.tabs)
    lateinit var tabLayout: TabLayout

    @BindView(R.id.view_pager)
    lateinit var viewPager: ViewPager

    @InjectPresenter
    lateinit var presenter: DetailScreenPresenter

    private lateinit var movieStatusesAdapter: MovieStatusesAdapter
    private var movie: Movie? = null
    private var movieId: Int = 0

    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    @ProvidePresenter
    internal fun providePresenter(): DetailScreenPresenter {
        movieId = arguments!!.getInt(TagUtils.MOVIE_ID)
        val app = TheApplication.getInstance().appComponent
        return DetailScreenPresenter(app!!.detailScreenInteractor, app.router, movieId)
    }

    override fun shareMovie(movie: Movie) {
        val shareMessage = getShareMessage(movie)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        intent.type = "text/plain"
        val chosenIntent = Intent.createChooser(intent, getString(R.string.share_to))
        startActivity(chosenIntent)
    }

    private fun getShareMessage(movie: Movie): String {
        return String.format(getString(R.string.share_message), movie.title, NetworkUtils.getBigPosterUrl(movie.posterPath!!))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Movie status
        initMovieStatusRecyclerView()
        initMovieStatusAdapter()

        initToolbar()
        initCollapsingToolbarLayout()

        initViewPager()
        initTabLayout()

        // Init listeners
        initOnBackDropClickListener()
    }

    private fun initOnBackDropClickListener() {
        backdropImage.setOnClickListener {
            presenter.onBackdropClicked(movie)
        }
    }

    private fun initTabLayout() {
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initViewPager() {
        val pagerAdapter = MoviePagerAdapter(childFragmentManager, context!!, movieId)
        viewPager.adapter = pagerAdapter
        viewPager.pageMargin = DimensionsUtils.convertDpToPixel(resources.getDimension(R.dimen.view_pager_margin), context!!).toInt()
    }

    private fun initMovieStatusAdapter() {
        movieStatusesAdapter = MovieStatusesAdapter(context!!)
        movieStatusesAdapter.setOnClickListener {
            presenter.onMovieStatusClicked(movie, it)
        }
        movieStatusRecyclerView.adapter = movieStatusesAdapter
    }

    private fun initMovieStatusRecyclerView() {
        movieStatusRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        movieStatusRecyclerView.addItemDecoration(
                MarginItemDecoration(context!!, 6, 6, 0, 0)
        )
    }

    override fun setMovieStatus(status: MovieStatus) {
        movieStatusesAdapter.setStatusHighlighted(status)
    }

    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        toolbar.inflateMenu(R.menu.detail_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.share_item) {
                presenter.onShareIconClicked(movie)
                return@setOnMenuItemClickListener true
            }
            false
        }
    }

    private fun initCollapsingToolbarLayout() {
        collapsingToolbarLayout.setExpandedTitleColor(resources.getColor(R.color.white))
        collapsingToolbarLayout.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        // TODO make it all dimens
        collapsingToolbarLayout.setExpandedTitleMargin(
                DimensionsUtils.convertDpToPixel(22.0f, context!!).toInt(),
                0,
                DimensionsUtils.convertDpToPixel(44.0f, context!!).toInt(),
                DimensionsUtils.convertDpToPixel(75.0f, context!!).toInt()
        )
    }

    override fun setMovieDetail(movie: Movie) {
        this.movie = movie
        collapsingToolbarLayout.title = movie.title
        textViewRating.text = movie.voteAverage.toString()
        movieStatusesAdapter.setStatusHighlighted(movie.status)

        movie.posterPath?.let {
            Glide.with(this)
                    .load(NetworkUtils.getBigPosterUrl(it))
                    .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                    .into(backdropImage)
        }
    }

    companion object {
        private const val FADE_OUT_DURATION = 100 // ms

        fun getInstance(movieId: Int): DetailScreenFragment {
            val fragment = DetailScreenFragment()
            val arguments = Bundle()
            arguments.putInt(TagUtils.MOVIE_ID, movieId)
            fragment.arguments = arguments
            return fragment
        }
    }
}
