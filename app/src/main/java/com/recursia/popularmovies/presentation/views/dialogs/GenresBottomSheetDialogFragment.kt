package com.recursia.popularmovies.presentation.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.presentation.presenters.GenresBottomSheetPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.adapters.common.MovieStretchItemType
import com.recursia.popularmovies.presentation.views.contracts.GenresBottomSheetDialogContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import com.recursia.popularmovies.presentation.views.dialogs.base.MvpBottomSheetDialogFragment
import com.recursia.popularmovies.utils.StringUtils
import com.recursia.popularmovies.utils.TagUtils
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class GenresBottomSheetDialogFragment : MvpBottomSheetDialogFragment(), GenresBottomSheetDialogContract {
    @BindView(R.id.recycler_view_movies)
    lateinit var recyclerViewMovies: RecyclerView

    @BindView(R.id.text_view_genre)
    lateinit var textViewGenre: TextView

    private lateinit var moviesAdapter: MoviesAdapter

    @InjectPresenter
    lateinit var presenter: GenresBottomSheetPresenter

    private var genre: Genre? = null

    @ProvidePresenter
    internal fun providePresenter(): GenresBottomSheetPresenter {
        val genreId = arguments!!.getInt(TagUtils.GENRE_ID)
        val app = TheApplication.getInstance().appComponent
        return GenresBottomSheetPresenter(app.genresBottomSheetInteractor, app.router, genreId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initRecyclerView()
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(context!!, MovieStretchItemType())
        moviesAdapter.setOnClickListener {
            presenter.onMovieClicked(it)
        }
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }

    private fun initRecyclerView() {
        recyclerViewMovies.layoutManager = GridLayoutManager(context!!, SPAN_COUNT)
        recyclerViewMovies.addItemDecoration(
                MarginItemDecoration(context!!, 10, 10, 5, 5)
        )
        recyclerViewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val isBottomReached = !recyclerView.canScrollVertically(DIRECTION_DOWN)
                if (isBottomReached) {
                    presenter.bottomIsReached(genre!!)
                }
            }
        })
        recyclerViewMovies.setHasFixedSize(true)
        recyclerViewMovies.adapter = moviesAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_genre_movies, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun setMovies(movies: List<Movie>) {
        moviesAdapter.setMovies(movies as MutableList<Movie>)
    }

    override fun addMovies(movies: List<Movie>) {
        moviesAdapter.addMovies(movies as MutableList<Movie>)
    }

    override fun setGenre(genre: Genre) {
        this.genre = genre
        textViewGenre.text = StringUtils.getCapitalized(genre.name!!)
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val DIRECTION_DOWN = 1
        fun getInstance(genreId: Int): GenresBottomSheetDialogFragment {
            val fragment = GenresBottomSheetDialogFragment()
            val args = Bundle()
            args.putInt(TagUtils.GENRE_ID, genreId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getTheme() = R.style.CustomBottomSheetDialog
}
