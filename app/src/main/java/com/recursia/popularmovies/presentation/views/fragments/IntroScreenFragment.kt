package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.presentation.models.SectionItem
import com.recursia.popularmovies.presentation.presenters.IntroScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.SectionsPagerAdapter
import com.recursia.popularmovies.presentation.views.contracts.IntroScreenContract
import com.recursia.popularmovies.utils.intro.PrefUtilsImpl
import java.util.*

class IntroScreenFragment : MvpAppCompatFragment(), IntroScreenContract {
    @BindView(R.id.section_view_pager)
    lateinit var sectionViewPager: ViewPager
    @BindView(R.id.finish_button)
    lateinit var finishButton: Button
    @BindView(R.id.previous_button)
    lateinit var previousButton: Button
    @BindView(R.id.next_button)
    lateinit var nextButton: Button
    @BindView(R.id.dots_linear_layout)
    lateinit var dotsLinearLayout: LinearLayout
    @InjectPresenter
    lateinit var presenter: IntroScreenPresenter
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var dots: MutableList<TextView>
    @ProvidePresenter
    fun providePresenter(): IntroScreenPresenter {
        val descriptions = resources.getStringArray(R.array.intro_descriptions)
        val filePaths = resources.getStringArray(R.array.intro_file_paths)
        val sectionItems: MutableList<SectionItem> = ArrayList()
        for (i in descriptions.indices) {
            sectionItems.add(SectionItem(filePaths[i], descriptions[i]))
        }
        val app = TheApplication.getInstance()
        return IntroScreenPresenter(PrefUtilsImpl(context!!), sectionItems, app.appComponent.router)
    }

    @OnClick(R.id.next_button)
    fun onNextButtonClicked() {
        presenter.onNextButtonClicked()
    }

    @OnClick(R.id.previous_button)
    fun onPreviousButtonClicked() {
        presenter.onPreviousButtonClicked()
    }

    @OnClick(R.id.finish_button)
    fun onFinishButtonClicked() {
        presenter.onFinishButtonClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionViewPager.adapter = sectionsPagerAdapter
        sectionViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                presenter.onPageSelected(i)
                updateDots(i)
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
    }

    private fun updateDots(position: Int) {
        for (dot in dots) {
            dot.setTextColor(resources.getColor(R.color.colorTransparentWhite))
        }
        dots[position].setTextColor(resources.getColor(R.color.white))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_intro, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun setViewPagerData(sectionItems: List<SectionItem>) {
        sectionsPagerAdapter.setSections(sectionItems)
        initDots()
    }

    private fun initDots() {
        dots = ArrayList()
        for (i in 0 until sectionsPagerAdapter.count) {
            val dot = TextView(context)
            dot.text = Html.fromHtml(DOT_FROM_HTML)
            dot.textSize = DOT_SIZE.toFloat()
            dot.setTextColor(if (i == 0) resources.getColor(R.color.white) else resources.getColor(R.color.colorTransparentWhite))
            dotsLinearLayout.addView(dot)
            dots.add(dot)
        }
    }

    override fun setNextSection() {
        sectionViewPager.currentItem = sectionViewPager.currentItem + 1
    }

    override fun setPreviousSection() {
        sectionViewPager.currentItem = sectionViewPager.currentItem - 1
    }

    override fun showNextButton() {
        nextButton.visibility = View.VISIBLE
    }

    override fun hideNextButton() {
        nextButton.visibility = View.GONE
    }

    override fun showPreviousButton() {
        previousButton.visibility = View.VISIBLE
    }

    override fun hidePreviousButton() {
        previousButton.visibility = View.GONE
    }

    override fun showFinishButton() {
        finishButton.visibility = View.VISIBLE
    }

    override fun hideFinishButton() {
        finishButton.visibility = View.GONE
    }

    companion object {
        private const val DOT_SIZE = 35
        private const val DOT_FROM_HTML = "&#8226"
        val instance: IntroScreenFragment
            get() = IntroScreenFragment()
    }
}
