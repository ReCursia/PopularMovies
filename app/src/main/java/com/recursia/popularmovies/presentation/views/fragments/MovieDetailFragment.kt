package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.presentation.presenters.MovieDetailPresenter
import com.recursia.popularmovies.presentation.views.adapters.CastAdapter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.contracts.MovieDetailContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import com.recursia.popularmovies.utils.TagUtils

class MovieDetailFragment : MvpAppCompatFragment(), MovieDetailContract {

    @BindView(R.id.description)
    lateinit var descriptionTextView: TextView

    @BindView(R.id.release_date)
    lateinit var releaseDateTextView: TextView

    @BindView(R.id.genres_group)
    lateinit var genresGroup: ChipGroup

    @BindView(R.id.recycler_view_cast)
    lateinit var recyclerViewCast: RecyclerView

    @BindView(R.id.recycler_view_recommendation)
    lateinit var recyclerViewRecommendation: RecyclerView

    private lateinit var castAdapter: CastAdapter
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
                MarginItemDecoration(context!!, 7, 7, 0, 0)
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

    override fun setMovieDetail(movie: Movie) {
        // Cast
        castAdapter.setCast(movie.casts)
        // Genres
        setGenres(movie.genres)
    }

    private fun setGenres(genres: List<Genre>) {
        genresGroup.removeAllViews()
        val layoutInflater = LayoutInflater.from(context)
        for (genre in genres) {
            val chip = layoutInflater.inflate(R.layout.genre_chip, genresGroup, false) as Chip
            //TODO add emoji
            chip.text = genre.name
            genresGroup.addView(chip)
        }
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }


}