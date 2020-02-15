package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.recursia.popularmovies.R

class MainScreenFragment : MvpAppCompatFragment() {
    @BindView(R.id.bottom_navigation_view)
    lateinit var navigationView: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirstScreen()
        initTabChangeListener()
    }

    private fun initTabChangeListener() { //TODO make it with presenter
        navigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.navigation_popular -> openPopularScreen()
                R.id.navigation_search -> openSearchScreen()
                R.id.navigation_favorite -> openFavoriteScreen()
                R.id.navigation_account -> {
                }
            }
            true
        }
    }

    private fun openFavoriteScreen() {
        childFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, FavoriteScreenFragment.getInstance())
                .commit()
    }

    private fun openSearchScreen() {
        childFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SearchScreenFragment.instance)
                .commit()
    }

    private fun openPopularScreen() {
        childFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, PopularScreenFragment.instance)
                .commit()
    }

    private fun initFirstScreen() {
        childFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, PopularScreenFragment.instance)
                .commit()
    }

    companion object {
        val instance: MainScreenFragment
            get() = MainScreenFragment()
    }
}