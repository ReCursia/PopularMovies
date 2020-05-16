package com.recursia.popularmovies.presentation.views.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.recursia.popularmovies.R
import com.recursia.popularmovies.presentation.views.fragments.MovieDetailFragment
import com.recursia.popularmovies.presentation.views.fragments.ReviewsFragment

class MoviePagerAdapter(
        fm: FragmentManager,
        private val context: Context,
        private val movieId: Int
) : FragmentStatePagerAdapter(fm) {

    private val titles = arrayOf(
            context.getString(R.string.about_movie_tab_title),
            context.getString(R.string.reviews_tab_title)
    )

    override fun getItem(i: Int) = getInstance(i)

    private fun getInstance(index: Int): Fragment {
        return when (index) {
            0 -> MovieDetailFragment.getInstance(movieId)
            1 -> ReviewsFragment.getInstance(movieId)
            else -> throw IllegalStateException()
        }
    }

    override fun getPageTitle(position: Int): String = titles[position]

    override fun getCount() = TAB_COUNT

    companion object {
        private const val TAB_COUNT = 2
    }
}
