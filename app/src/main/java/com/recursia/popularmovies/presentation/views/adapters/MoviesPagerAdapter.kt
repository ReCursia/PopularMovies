package com.recursia.popularmovies.presentation.views.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.recursia.popularmovies.presentation.views.fragments.MoviesListFragment
import com.recursia.popularmovies.utils.NetworkUtils
import com.recursia.popularmovies.utils.TagUtils

class MoviesPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val argsList = arrayOf(NetworkUtils.POPULARITY, NetworkUtils.TOP_RATED)
    private val titles = arrayOf("POPULAR", "TOP RATED")

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getItem(i: Int): Fragment {
        return getInstance(i)
    }

    private fun getInstance(index: Int): Fragment {
        val fragment = MoviesListFragment()
        //Passing args
        val args = Bundle()
        args.putString(TagUtils.FRAGMENT_MOVIES, argsList[index])
        fragment.arguments = args
        return fragment
    }

    override fun getCount(): Int {
        return TAB_COUNT
    }

    companion object {
        private const val TAB_COUNT = 2
    }

}
