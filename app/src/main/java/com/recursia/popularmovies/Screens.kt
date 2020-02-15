package com.recursia.popularmovies

import android.support.v4.app.Fragment
import com.recursia.popularmovies.presentation.views.fragments.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class DetailScreen(private val movieId: Int) : SupportAppScreen() {

        override fun getFragment(): Fragment {
            return DetailScreenFragment.getInstance(movieId)
        }
    }

    class FavoriteScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return FavoriteScreenFragment.getInstance() // TODO make it val instance? with caps
        }
    }

    class IntroScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return IntroScreenFragment.instance
        }
    }

    class PopularScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return PopularScreenFragment.instance
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

    class MainScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MainScreenFragment.instance
        }
    }
}
