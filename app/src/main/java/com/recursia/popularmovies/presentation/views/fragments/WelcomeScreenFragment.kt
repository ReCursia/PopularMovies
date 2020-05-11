package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.recursia.popularmovies.R
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.TheApplication
import moxy.MvpAppCompatFragment
import ru.terrakok.cicerone.Router

class WelcomeScreenFragment : MvpAppCompatFragment() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.welcome_next_button)
    lateinit var welcomeNextButton: Button

    lateinit var router: Router

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account_welcome, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = TheApplication.getInstance().appComponent.router
        initView()
    }

    private fun initView() {
        toolbar.title = resources.getString(R.string.account_title)
        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { router.exit() }
        welcomeNextButton.setOnClickListener { router.replaceScreen(Screens.AuthScreen()) }
    }

    companion object {
        fun getInstance() = WelcomeScreenFragment()
    }
}