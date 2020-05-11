package com.recursia.popularmovies.presentation.views.fragments

import com.recursia.popularmovies.presentation.views.contracts.AccountScreenContract
import moxy.MvpAppCompatFragment

class AccountScreenFragment : MvpAppCompatFragment(), AccountScreenContract {

    companion object {
        fun getInstance() = AccountScreenFragment()
    }
}