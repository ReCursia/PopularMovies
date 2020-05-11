package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Review
import com.recursia.popularmovies.presentation.presenters.ReviewsPresenter
import com.recursia.popularmovies.presentation.views.adapters.ReviewsAdapter
import com.recursia.popularmovies.presentation.views.contracts.ReviewsContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import com.recursia.popularmovies.utils.TagUtils
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ReviewsFragment : MvpAppCompatFragment(), ReviewsContract {

    @BindView(R.id.recycler_view_reviews)
    lateinit var recyclerViewReviews: RecyclerView

    @InjectPresenter
    lateinit var presenter: ReviewsPresenter

    private var movieId = 0


    @ProvidePresenter
    internal fun providePresenter(): ReviewsPresenter {
        movieId = arguments!!.getInt(TagUtils.MOVIE_ID)
        val app = TheApplication.getInstance().appComponent
        return ReviewsPresenter(app!!.detailScreenInteractor, movieId)
    }

    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reviews, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Reviews
        initReviewsRecyclerView()
        initReviewsAdapter()
    }

    private fun initReviewsAdapter() {
        reviewsAdapter = ReviewsAdapter(context!!)
        reviewsAdapter.setOnClickListener { review: Review, position: Int ->
            presenter.onTranslateReviewClicked(review, position)
        }
        recyclerViewReviews.adapter = reviewsAdapter
    }

    private fun initReviewsRecyclerView() {
        recyclerViewReviews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerViewReviews.addItemDecoration(
                MarginItemDecoration(context!!, 0, 0, 5, 5)
        )
    }

    companion object {
        fun getInstance(movieId: Int): ReviewsFragment {
            val fragment = ReviewsFragment()
            val arguments = Bundle()
            arguments.putInt(TagUtils.MOVIE_ID, movieId)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun updateReview(review: Review, position: Int) {
        reviewsAdapter.updateReview(review, position)
    }

    override fun setReviews(reviews: List<Review>) {
        reviewsAdapter.setReviews(reviews as MutableList<Review>)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }
}