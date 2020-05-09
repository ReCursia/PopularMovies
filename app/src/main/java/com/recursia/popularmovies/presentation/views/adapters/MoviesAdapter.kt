package com.recursia.popularmovies.presentation.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.recursia.popularmovies.R
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.utils.NetworkUtils

class MoviesAdapter(private val context: Context,
                    private val isRecommendationMovies: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movies: MutableList<Movie> = ArrayList()
    private var clickListener: ((Movie) -> Unit)? = null

    fun setClickListener(clickListener: (Movie) -> Unit) {
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

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return if (isRecommendationMovies) {
            val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.movie_item, viewGroup, false)
            RecommendationMovieViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.movie_item, viewGroup, false)
            MovieViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(movieViewHolder: RecyclerView.ViewHolder, i: Int) {
        val movie = movies[i]
        if (isRecommendationMovies) {
            setRecommendationMovieData(movie, movieViewHolder as RecommendationMovieViewHolder)
        } else {
            setPlainMovieData(movie, movieViewHolder as MovieViewHolder)
        }
    }

    private fun setRecommendationMovieData(movie: Movie, movieViewHolder: RecommendationMovieViewHolder) {
        // Title
        movieViewHolder.movieTitleTextView.text = movie.title
        // Rating
        movieViewHolder.movieRatingTextView.text = movie.voteAverage.toString()
        // Image
        Glide.with(context)
                .load(NetworkUtils.getMediumPosterUrl(movie.posterPath!!))
                .placeholder(R.drawable.ic_poster_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(movieViewHolder.moviePoster)
        // Listener
        movieViewHolder.itemView.setOnClickListener {
            clickListener?.invoke(movie)
        }
    }

    private fun setPlainMovieData(movie: Movie, movieViewHolder: MovieViewHolder) {
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

    internal inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.smallMoviePoster)
        lateinit var moviePoster: ImageView

        @BindView(R.id.movieTitleTextView)
        lateinit var movieTitleTextView: TextView

        @BindView(R.id.movieRatingTextView)
        lateinit var movieRatingTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    internal inner class RecommendationMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.smallMoviePoster)
        lateinit var moviePoster: ImageView

        @BindView(R.id.movieTitleTextView)
        lateinit var movieTitleTextView: TextView

        @BindView(R.id.movieRatingTextView)
        lateinit var movieRatingTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    companion object {
        private const val FADE_OUT_DURATION = 100 // ms
    }
}
