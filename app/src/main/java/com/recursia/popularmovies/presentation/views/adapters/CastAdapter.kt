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
import com.recursia.popularmovies.domain.models.Cast
import com.recursia.popularmovies.utils.NetworkUtils
import java.util.*

class CastAdapter(private val context: Context) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    private var cast: List<Cast> = ArrayList()

    fun setCast(cast: List<Cast>) {
        this.cast = cast
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cast.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CastViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.cast_item, viewGroup, false)
        return CastViewHolder(itemView)
    }

    override fun onBindViewHolder(movieViewHolder: CastViewHolder, i: Int) {
        val castItem = cast[i]
        // Image
        Glide.with(context)
                .load(NetworkUtils.getMediumProfileUrl(castItem.profilePath ?: ""))
                .placeholder(R.drawable.ic_cast_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(movieViewHolder.castImage)
        // Name
        movieViewHolder.castName.text = castItem.name
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.castImage)
        lateinit var castImage: ImageView
        @BindView(R.id.castName)
        lateinit var castName: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    companion object {
        private const val FADE_OUT_DURATION = 100 // ms
    }
}
