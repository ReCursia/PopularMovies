package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.presentation.presenters.PopularScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesPagerAdapter
import com.recursia.popularmovies.presentation.views.contracts.PopularScreenContract

class PopularScreenFragment : MvpAppCompatFragment(), PopularScreenContract {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.tabs)
    lateinit var tabLayout: TabLayout
    @BindView(R.id.movies_view_pager)
    lateinit var moviesViewPager: ViewPager
    @InjectPresenter
    lateinit var presenter: PopularScreenPresenter

    @ProvidePresenter
    fun providePresenter(): PopularScreenPresenter {
        val app = TheApplication.getInstance().appComponent
        return PopularScreenPresenter(app.router)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initViewPager()
        initTabLayout()
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
        toolbar.setBackgroundColor(resources.getColor(R.color.black))
    }

    private fun initViewPager() {
        val pagerAdapter = MoviesPagerAdapter(childFragmentManager)
        moviesViewPager.adapter = pagerAdapter
    }

    private fun initTabLayout() {
        tabLayout.setupWithViewPager(moviesViewPager)
    }

    companion object {
        val instance: PopularScreenFragment
            get() = PopularScreenFragment()
    }
}