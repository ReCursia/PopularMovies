package com.recursia.popularmovies

import androidx.fragment.app.Fragment
import com.recursia.popularmovies.presentation.views.fragments.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class DetailScreen(private val movieId: Int) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return DetailScreenFragment.getInstance(movieId)
        }
    }

    class MainScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MainScreenFragment.getInstance()
        }
    }

    class WelcomeScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return WelcomeScreenFragment.getInstance()
        }
    }

    class AccountScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AccountScreenFragment.getInstance()
        }
    }

    class ResetPasswordScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ResetPasswordScreenFragment.getInstance()
        }
    }

    class AuthScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AuthScreenFragment.getInstance()
        }
    }

    class IntroScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return IntroScreenFragment.instance
        }
    }

    class PhotoScreen(private val imagePath: String) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return PhotoScreenFragment.getInstance(imagePath)
        }
    }

    class SearchScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SearchScreenFragment.instance
        }
    }
}
