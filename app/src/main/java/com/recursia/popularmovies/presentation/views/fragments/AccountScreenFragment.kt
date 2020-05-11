package com.recursia.popularmovies.presentation.views.fragments

import com.arellomobile.mvp.MvpAppCompatFragment
import com.recursia.popularmovies.presentation.views.contracts.AccountScreenContract

class AccountScreenFragment : MvpAppCompatFragment(), AccountScreenContract {

    companion object {
        fun getInstance() = AccountScreenFragment()
    }
}