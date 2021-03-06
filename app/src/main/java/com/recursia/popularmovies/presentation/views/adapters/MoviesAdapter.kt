package com.recursia.popularmovies.presentation.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.recursia.popularmovies.R
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.presentation.views.adapters.common.ItemType
import com.recursia.popularmovies.utils.NetworkUtils

class MoviesAdapter(
    private val context: Context,
    private val itemType: ItemType,
    val tag: String? = null
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private var movies: MutableList<Movie> = ArrayList()
    private var clickListener: ((Movie) -> Unit)? = null

    fun setOnClickListener(clickListener: (Movie) -> Unit) {
        this.clickListener = clickListener
    }

    fun setMovies(movies: MutableList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun addMovies(newMovies: List<Movie>) {
        val posBefore = itemCount
        movies.addAll(newMovies)
        notifyItemRangeInserted(posBefore, newMovies.size)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        val layoutId = itemType.getLayoutId()
        val itemView = LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, i: Int) {
        val movie = movies[i]
        setMovieData(movie, movieViewHolder)
    }

    private fun setMovieData(movie: Movie, movieViewHolder: MovieViewHolder) {
        // Title
        movieViewHolder.movieTitleTextView.text = movie.title
        // Rating
        movieViewHolder.movieRatingTextView.text = movie.voteAverage.toString()
        // Image
        Glide.with(context)
                .load(NetworkUtils.getMediumPosterUrl(movie.posterPath ?: ""))
                .placeholder(R.drawable.ic_poster_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(movieViewHolder.moviePoster)
        // Listener
        movieViewHolder.itemView.setOnClickListener {
            clickListener?.invoke(movie)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.small_movie_poster)
        lateinit var moviePoster: ImageView

        @BindView(R.id.movie_title_text_view)
        lateinit var movieTitleTextView: TextView

        @BindView(R.id.movie_rating_text_view)
        lateinit var movieRatingTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    companion object {
        private const val FADE_OUT_DURATION = 100 // ms
    }
}
