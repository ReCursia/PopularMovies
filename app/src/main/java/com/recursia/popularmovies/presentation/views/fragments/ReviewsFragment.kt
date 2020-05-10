package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
import com.recursia.popularmovies.utils.TagUtils

class ReviewsFragment : MvpAppCompatFragment() {
    companion object {
        fun getInstance(movieId: Int): ReviewsFragment {
            val fragment = ReviewsFragment()
            val arguments = Bundle()
            arguments.putInt(TagUtils.MOVIE_ID, movieId)
            fragment.arguments = arguments
            return fragment
        }
    }
}