package com.recursia.popularmovies.presentation.views.fragments

import android.content.Intent
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.presentation.presenters.DetailScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.CastAdapter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.adapters.TrailersAdapter
import com.recursia.popularmovies.presentation.views.contracts.DetailScreenContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import com.recursia.popularmovies.utils.DateUtils
import com.recursia.popularmovies.utils.NetworkUtils
import com.recursia.popularmovies.utils.TagUtils

class DetailScreenFragment : MvpAppCompatFragment(), DetailScreenContract {
    @BindView(R.id.descriptionTextView)
    internal lateinit var descriptionTextView: TextView

    @BindView(R.id.ratingTextView)
    internal lateinit var ratingTextView: TextView

    @BindView(R.id.originalTitleTextView)
    internal lateinit var originalTitleTextView: TextView

    @BindView(R.id.favoriteIcon)
    internal lateinit var favoriteIcon: FloatingActionButton

    @BindView(R.id.releaseDateTextView)
    internal lateinit var releaseDateTextView: TextView

    @BindView(R.id.recycleViewTrailers)
    internal lateinit var recyclerViewTrailers: RecyclerView

    @BindView(R.id.backdropImage)
    internal lateinit var backdropImage: ImageView

    @BindView(R.id.toolbar)
    internal lateinit var toolbar: Toolbar

    @BindView(R.id.collapsingToolbar)
    internal lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    @BindView(R.id.genresGroup)
    internal lateinit var genresGroup: ChipGroup

    @BindView(R.id.recyclerViewCast)
    internal lateinit var recyclerViewCast: RecyclerView

    @BindView(R.id.castCardView)
    internal lateinit var castCardView: CardView

    @BindView(R.id.descriptionCardView)
    internal lateinit var descriptionCardView: CardView

    @BindView(R.id.detailCardView)
    internal lateinit var detailCardView: CardView

    @BindView(R.id.recyclerViewMovieRecommendations)
    internal lateinit var recyclerViewMovieRecommendations: RecyclerView

    @BindView(R.id.movieRecommendationCardView)
    internal lateinit var movieRecommendationCardView: CardView

    @InjectPresenter
    internal lateinit var presenter: DetailScreenPresenter
    private lateinit var trailersAdapter: TrailersAdapter
    private lateinit var castAdapter: CastAdapter
    private lateinit var moviesAdapter: MoviesAdapter
    private var movie: Movie? = null

    override fun showFavoriteIcon() {
        favoriteIcon.show()
    }

    override fun hideFavoriteIcon() {
        favoriteIcon.hide()
    }

    @OnClick(R.id.favoriteIcon)
    fun onFavoriteIconClicked() {
        presenter.onFavoriteIconClicked(movie)
    }

    @OnClick(R.id.backdropImage)
    fun onBackdropImageClicked() {
        presenter.onBackdropImageClicked(movie)
    }

    override fun setRecommendationMovies(movies: List<Movie>) {
        if (movies.isNotEmpty()) {
            moviesAdapter.setMovies(movies as MutableList<Movie>)
        }
    }

    override fun hideRecommendationMovies() {
        movieRecommendationCardView.visibility = View.GONE
    }

    override fun showRecommendationMovies() {
        movieRecommendationCardView.visibility = View.VISIBLE
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    @ProvidePresenter
    internal fun providePresenter(): DetailScreenPresenter {
        val movieId = arguments!!.getInt(TagUtils.MOVIE_ID)
        val app = TheApplication.getInstance().appComponent
        return DetailScreenPresenter(app!!.detailScreenInteractor, app.router, movieId)
    }

    override fun setFavoriteIconOn() {
        favoriteIcon.setImageDrawable(context!!.getDrawable(R.drawable.ic_favorite_on))
        favoriteIcon.imageMatrix = Matrix() // trick
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shareItem) {
            presenter.onShareIconClicked(movie)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMovieAddedMessage() {
        Toast.makeText(context, getString(R.string.added_favorite), Toast.LENGTH_LONG).show()
    }

    override fun showMovieRemovedMessage() {
        Toast.makeText(context, getString(R.string.removed_favorite), Toast.LENGTH_LONG).show()
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

    override fun hideMovieDetail() {
        detailCardView.visibility = View.GONE
        descriptionCardView.visibility = View.GONE
        recyclerViewTrailers.visibility = View.GONE
        castCardView.visibility = View.GONE
    }

    override fun showMovieDetail() {
        detailCardView.visibility = View.VISIBLE
        descriptionCardView.visibility = View.VISIBLE
        recyclerViewTrailers.visibility = View.VISIBLE
        castCardView.visibility = View.VISIBLE
    }

    override fun setFavoriteIconOff() {
        favoriteIcon.setImageDrawable(context!!.getDrawable(R.drawable.ic_favorite_off))
        favoriteIcon.imageMatrix = Matrix() // trick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Trailers
        initTrailerRecyclerView()
        initTrailersAdapter()
        // Cast
        initCastRecyclerView()
        initCreditsAdapter()
        // Movie recommendation
        initMovieRecommendationRecyclerView()
        initMovieRecommendationAdapter()

        initToolbar()
        initCollapsingToolbarLayout()
    }

    private fun initTrailerRecyclerView() {
        recyclerViewTrailers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTrailers.setHasFixedSize(true)
        recyclerViewTrailers.addItemDecoration(
                MarginItemDecoration(context!!, 7, 7, 0, 0))
    }

    private fun initTrailersAdapter() {
        trailersAdapter = TrailersAdapter(context!!)
        trailersAdapter.setClickListener {
            presenter.onTrailerPlayButtonClicked(it)
        }
        recyclerViewTrailers.adapter = trailersAdapter
    }

    private fun initCastRecyclerView() {
        recyclerViewCast.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCast.setHasFixedSize(true)
        recyclerViewCast.addItemDecoration(
                MarginItemDecoration(context!!, 20, 0, 0, 0))
    }

    private fun initCreditsAdapter() {
        castAdapter = CastAdapter(context!!)
        recyclerViewCast.adapter = castAdapter
    }

    private fun initMovieRecommendationRecyclerView() {
        recyclerViewMovieRecommendations.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewMovieRecommendations.setHasFixedSize(true)
        recyclerViewMovieRecommendations.addItemDecoration(
                MarginItemDecoration(context!!, 7, 7, 0, 0))
    }

    private fun initMovieRecommendationAdapter() {
        moviesAdapter = MoviesAdapter(context!!, IS_RECOMMENDATION_MOVIES)
        moviesAdapter.setClickListener {
            presenter.onMovieClicked(it)
        }
        recyclerViewMovieRecommendations.adapter = moviesAdapter
    }

    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        toolbar.inflateMenu(R.menu.detail_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.shareItem) {
                presenter.onShareIconClicked(movie)
                return@setOnMenuItemClickListener true
            }
            false
        }
    }

    private fun initCollapsingToolbarLayout() {
        collapsingToolbarLayout.setExpandedTitleColor(resources.getColor(R.color.white))
        collapsingToolbarLayout.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    override fun openTrailerUrl(trailer: Trailer) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.TRAILER_BASE_URL + trailer.key))
        startActivity(browserIntent)
    }

    override fun setMovieDetail(movie: Movie) {
        this.movie = movie
        // Trailers
        val trailers = movie.trailers
        if (trailers.isNotEmpty()) {
            trailersAdapter.setTrailers(trailers)
        }
        // Genres
        val genres = movie.genres
        if (genres.isNotEmpty()) {
            setGenres(genres)
        }
        // Cast
        val casts = movie.casts
        if (casts.isNotEmpty()) {
            castAdapter.setCast(casts)
        }
        // Trailers
        collapsingToolbarLayout.title = movie.title
        originalTitleTextView.text = movie.originalTitle
        releaseDateTextView.text = DateUtils.formatDate(movie.releaseDate!!)
        descriptionTextView.text = movie.overview
        ratingTextView.text = movie.voteAverage.toString()
        // Image
        movie.backdropPath?.let {
            Glide.with(this)
                    .load(NetworkUtils.getBigPosterUrl(it))
                    .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                    .into(backdropImage)
        }
    }

    private fun setGenres(genres: List<Genre>) {
        // Before set genres remove previous one
        genresGroup.removeAllViews()
        val layoutInflater = LayoutInflater.from(context)
        for (genre in genres) {
            val chip = layoutInflater.inflate(R.layout.genre_chip, genresGroup, false) as Chip
            chip.text = genre.name
            genresGroup.addView(chip)
        }
    }

    companion object {
        private const val FADE_OUT_DURATION = 100 // ms
        private const val IS_RECOMMENDATION_MOVIES = true

        fun getInstance(movieId: Int): DetailScreenFragment {
            val fragment = DetailScreenFragment()
            val arguments = Bundle()
            arguments.putInt(TagUtils.MOVIE_ID, movieId)
            fragment.arguments = arguments
            return fragment
        }
    }
}
