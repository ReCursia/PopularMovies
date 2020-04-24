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
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.utils.NetworkUtils
import java.util.*

class TrailersAdapter(private val context: Context) : RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>() {
    private var trailers: List<Trailer> = ArrayList()
    private var clickListener: ((Trailer) -> Unit)? = null

    fun setClickListener(clickListener: (Trailer) -> Unit) {
        this.clickListener = clickListener
    }

    fun setTrailers(trailers: List<Trailer>) {
        this.trailers = trailers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TrailerViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.trailer_item, viewGroup, false)
        return TrailerViewHolder(itemView)
    }

    override fun onBindViewHolder(trailerViewHolder: TrailerViewHolder, i: Int) {
        val trailer = trailers[i]
        // Image
        Glide.with(context)
                .load(String.format(NetworkUtils.TRAILER_IMAGE_FORMAT_URL, trailer.key))
                .placeholder(R.drawable.ic_trailer_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(trailerViewHolder.trailerImage)
        // Title
        trailerViewHolder.trailerTitle.text = trailer.name
        // Play button
        trailerViewHolder.playButton.setOnClickListener {
            clickListener?.invoke(trailer)
        }
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    inner class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.playButton)
        lateinit var playButton: ImageView

        @BindView(R.id.trailerTitle)
        lateinit var trailerTitle: TextView

        @BindView(R.id.trailerImage)
        lateinit var trailerImage: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    companion object {
        private const val FADE_OUT_DURATION = 100 // ms
    }
}
