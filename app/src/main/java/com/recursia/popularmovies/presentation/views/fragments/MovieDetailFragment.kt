package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
import com.recursia.popularmovies.utils.TagUtils

class MovieDetailFragment : MvpAppCompatFragment() {
    companion object {
        fun getInstance(movieId: Int): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val arguments = Bundle()
            arguments.putInt(TagUtils.MOVIE_ID, movieId)
            fragment.arguments = arguments
            return fragment
        }
    }

}