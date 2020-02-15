package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.lottie.LottieAnimationView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.recursia.popularmovies.R
import com.recursia.popularmovies.presentation.models.SectionItem
import com.recursia.popularmovies.utils.TagUtils.FRAGMENT_INTRO

class SectionScreenFragment : MvpAppCompatFragment() {
    @BindView(R.id.section_lottieAnimation)
    lateinit var sectionImage: LottieAnimationView
    @BindView(R.id.section_textView)
    lateinit var sectionText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
        //Setting data
        val bundle = arguments
        val item = bundle!!.getSerializable(FRAGMENT_INTRO) as SectionItem
        setSectionData(item)
    }

    private fun setSectionData(item: SectionItem) { //Image
        sectionImage.setAnimation(item.fileName)
        sectionImage.playAnimation()
        //Text
        sectionText.text = item.description
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro_section, container, false)
    }
}