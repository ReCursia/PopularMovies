package com.recursia.popularmovies.presentation.views.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.recursia.popularmovies.presentation.models.SectionItem
import com.recursia.popularmovies.presentation.views.fragments.SectionScreenFragment
import com.recursia.popularmovies.utils.TagUtils
import java.util.*

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var sectionItemList: List<SectionItem> = ArrayList()

    fun setSections(sectionItemList: List<SectionItem>) {
        this.sectionItemList = sectionItemList
        notifyDataSetChanged()
    }

    override fun getItem(i: Int): Fragment {
        return getInstance(i)
    }

    private fun getInstance(index: Int): Fragment {
        val fragment = SectionScreenFragment()
        //Passing args
        val args = Bundle()
        args.putSerializable(TagUtils.FRAGMENT_INTRO, sectionItemList[index])
        fragment.arguments = args
        return fragment
    }

    override fun getCount(): Int {
        return sectionItemList.size
    }

}
