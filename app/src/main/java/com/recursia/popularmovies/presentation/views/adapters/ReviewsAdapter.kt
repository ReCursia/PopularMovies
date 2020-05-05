package com.recursia.popularmovies.presentation.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.recursia.popularmovies.R
import com.recursia.popularmovies.domain.models.Review
import com.recursia.popularmovies.utils.TextViewUtils

class ReviewsAdapter(private val context: Context) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {
    private var reviews: MutableList<Review> = ArrayList()
    private var clickListener: ((Review, Int) -> Unit)? = null

    fun setReviews(reviews: MutableList<Review>) {
        this.reviews = reviews
        notifyDataSetChanged()
    }

    fun setClickListener(clickListener: (Review, Int) -> Unit) {
        this.clickListener = clickListener
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(reviewViewHolder: ReviewViewHolder, i: Int) {
        val review = reviews[i]
        // Author
        reviewViewHolder.author.text = review.author
        // Text
        reviewViewHolder.text.text = review.text
        // Translate
        reviewViewHolder.translateText.setOnClickListener {
            clickListener?.invoke(review, i)
        }
        // Show more
        if (shouldShowMoreText(review.text)) {
            reviewViewHolder.showMore.visibility = View.VISIBLE
            TextViewUtils.collapseTextView(reviewViewHolder.text, LINE_LIMIT)
        } else {
            reviewViewHolder.showMore.visibility = View.GONE
        }
        reviewViewHolder.showMore.setOnClickListener {
            TextViewUtils.expandTextViewWithAnimation(reviewViewHolder.text, ANIMATION_DURATION)
            reviewViewHolder.showMore.visibility = View.GONE
        }
    }

    private fun shouldShowMoreText(text: String?) = text?.length!! > TEXT_LENGTH_LIMIT

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.review_item, viewGroup, false)
        return ReviewViewHolder(itemView)
    }

    fun updateReview(review: Review, position: Int) {
        reviews[position] = review
        notifyItemChanged(position)
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.author)
        lateinit var author: TextView

        @BindView(R.id.text)
        lateinit var text: TextView

        @BindView(R.id.translate_text)
        lateinit var translateText: TextView

        @BindView(R.id.show_more)
        lateinit var showMore: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    companion object {
        private const val TEXT_LENGTH_LIMIT = 400
        private const val LINE_LIMIT = 5
        private const val ANIMATION_DURATION = 200L // ms
    }
}
