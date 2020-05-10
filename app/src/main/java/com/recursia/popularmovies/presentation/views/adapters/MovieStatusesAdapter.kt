package com.recursia.popularmovies.presentation.views.adapters

import android.content.Context
import android.support.design.card.MaterialCardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.recursia.popularmovies.R
import com.recursia.popularmovies.domain.models.enums.MovieStatus

class MovieStatusesAdapter(private val context: Context) : RecyclerView.Adapter<MovieStatusesAdapter.MovieStatusViewHolder>() {
    private var clickListener: ((MovieStatus) -> Unit)? = null
    private val statuses = MovieStatus.values().filter { it != MovieStatus.UNKNOWN }
    private val isHighlighted = Array(SIZE) { false }

    fun setOnClickListener(clickListener: (MovieStatus) -> Unit) {
        this.clickListener = clickListener
    }

    fun setStatusHighlighted(status: MovieStatus) {
        val index = statuses.indexOf(status)
        for (i in isHighlighted.indices) {
            isHighlighted[i] = false
        }
        isHighlighted[index] = true
        notifyDataSetChanged()
    }

    inner class MovieStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.card_view)
        lateinit var cardView: MaterialCardView

        @BindView(R.id.status_emoji_text_view)
        lateinit var statusEmojiTextView: TextView

        @BindView(R.id.status_text_view)
        lateinit var statusTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieStatusViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.movie_status_item, viewGroup, false)
        return MovieStatusViewHolder(itemView)
    }

    override fun getItemCount() = SIZE

    override fun onBindViewHolder(movieStatusViewHolder: MovieStatusViewHolder, index: Int) {
        val status = statuses[index]
        //TODO move it factory class and make view model
        val emoji = when (status) {
            MovieStatus.ALREADY_SAW -> 128522
            MovieStatus.WANT_TO_WATCH -> 128522
            MovieStatus.FAVORITE -> 128522
            MovieStatus.UNKNOWN -> throw IllegalStateException()
        }
        val text = when (status) {
            MovieStatus.ALREADY_SAW -> context.getString(R.string.status_already_saw)
            MovieStatus.WANT_TO_WATCH -> context.getString(R.string.status_want_to_watch)
            MovieStatus.FAVORITE -> context.getString(R.string.status_favorite)
            MovieStatus.UNKNOWN -> throw IllegalStateException()
        }
        movieStatusViewHolder.statusEmojiTextView.text = getEmojiByUnicode(emoji)
        movieStatusViewHolder.statusTextView.text = text

        val colorId = if (isHighlighted[index]) R.color.almostWhite else R.color.lightGray
        movieStatusViewHolder.cardView.strokeColor = context.resources.getColor(colorId)
        movieStatusViewHolder.statusTextView.setTextColor(context.resources.getColor(colorId))

        movieStatusViewHolder.itemView.setOnClickListener {
            clickListener?.invoke(status)
        }
    }

    private fun getEmojiByUnicode(unicode: Int): String? {
        return String(Character.toChars(unicode))
    }

    companion object {
        private val SIZE = MovieStatus.values().filter { it != MovieStatus.UNKNOWN }.size
    }
}