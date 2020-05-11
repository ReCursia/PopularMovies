package com.recursia.popularmovies.presentation.views.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.presentation.presenters.MovieDetailPresenter
import com.recursia.popularmovies.presentation.views.adapters.CastAdapter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.adapters.TrailersAdapter
import com.recursia.popularmovies.presentation.views.contracts.MovieDetailContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import com.recursia.popularmovies.utils.DateUtils
import com.recursia.popularmovies.utils.NetworkUtils
import com.recursia.popularmovies.utils.TagUtils
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MovieDetailFragment : MvpAppCompatFragment(), MovieDetailContract {

    @BindView(R.id.description)
    lateinit var descriptionTextView: TextView

    @BindView(R.id.release_date)
    lateinit var releaseDateTextView: TextView

    @BindView(R.id.genres_group)
    lateinit var genresGroup: ChipGroup

    @BindView(R.id.recycler_view_trailers)
    lateinit var recyclerViewTrailers: RecyclerView

    @BindView(R.id.recycler_view_cast)
    lateinit var recyclerViewCast: RecyclerView

    @BindView(R.id.recycler_view_recommendation)
    lateinit var recyclerViewRecommendation: RecyclerView

    private lateinit var castAdapter: CastAdapter
    private lateinit var trailersAdapter: TrailersAdapter
    private lateinit var moviesAdapter: MoviesAdapter

    @InjectPresenter
    lateinit var presenter: MovieDetailPresenter

    private var movieId: Int = 0


    @ProvidePresenter
    internal fun providePresenter(): MovieDetailPresenter {
        movieId = arguments!!.getInt(TagUtils.MOVIE_ID)
        val app = TheApplication.getInstance().appComponent
        return MovieDetailPresenter(app!!.detailScreenInteractor, app.router, movieId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Cast
        initCastRecyclerView()
        initCastAdapter()
        // Movie recommendations
        initRecommendationRecyclerView()
        initRecommendationAdapter()
        // Trailers
        initTrailersRecyclerView()
        initTrailersAdapter()
    }

    private fun initTrailersAdapter() {
        trailersAdapter = TrailersAdapter(context!!)
        trailersAdapter.setOnClickListener {
            presenter.onTrailerClicked(it)
        }
        recyclerViewTrailers.adapter = trailersAdapter
    }

    private fun initTrailersRecyclerView() {
        recyclerViewTrailers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTrailers.setHasFixedSize(true)
        recyclerViewTrailers.addItemDecoration(
                MarginItemDecoration(context!!, 0, 10, 0, 0)
        )
    }

    private fun initRecommendationAdapter() {
        moviesAdapter = MoviesAdapter(context!!)
        moviesAdapter.setOnClickListener {
            presenter.onMovieClicked(it)
        }
        recyclerViewRecommendation.adapter = moviesAdapter
    }

    private fun initRecommendationRecyclerView() {
        recyclerViewRecommendation.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewRecommendation.setHasFixedSize(true)
        recyclerViewRecommendation.addItemDecoration(
                MarginItemDecoration(context!!, 13, 1, 0, 0)
        )
    }

    private fun initCastAdapter() {
        castAdapter = CastAdapter(context!!)
        recyclerViewCast.adapter = castAdapter
    }

    private fun initCastRecyclerView() {
        recyclerViewCast.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCast.setHasFixedSize(true)
        recyclerViewCast.addItemDecoration(
                MarginItemDecoration(context!!, 20, 0, 0, 0)
        )
    }

    companion object {
        fun getInstance(movieId: Int): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val arguments = Bundle()
            arguments.putInt(TagUtils.MOVIE_ID, movieId)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun setRecommendationMovies(movies: List<Movie>) {
        moviesAdapter.setMovies(movies as MutableList<Movie>)
    }

    override fun openTrailerUrl(trailer: Trailer) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.TRAILER_BASE_URL + trailer.key))
        startActivity(browserIntent)
    }

    override fun setMovieDetail(movie: Movie) {
        // Description
        descriptionTextView.text = movie.overview
        // Release date
        releaseDateTextView.text = DateUtils.formatDate(movie.releaseDate!!)
        // Cast
        castAdapter.setCast(movie.casts)
        // Genres
        setGenres(movie.genres)
        // Trailers
        trailersAdapter.setTrailers(movie.trailers)
    }

    private fun setGenres(genres: List<Genre>) {
        genresGroup.removeAllViews()
        val layoutInflater = LayoutInflater.from(context)
        for (genre in genres) {
            val chip = layoutInflater.inflate(R.layout.genre_chip, genresGroup, false) as Chip

            chip.text = getCapitalized(genre.name!!)
            genresGroup.addView(chip)
        }
    }

    private fun getCapitalized(str: String) = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase()

    override fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }


}