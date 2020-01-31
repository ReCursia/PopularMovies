package com.recursia.popularmovies;

import android.support.v4.app.Fragment;

import com.recursia.popularmovies.presentation.views.fragments.DetailScreenFragment;
import com.recursia.popularmovies.presentation.views.fragments.FavoriteScreenFragment;
import com.recursia.popularmovies.presentation.views.fragments.IntroScreenFragment;
import com.recursia.popularmovies.presentation.views.fragments.MainScreenFragment;
import com.recursia.popularmovies.presentation.views.fragments.PhotoScreenFragment;
import com.recursia.popularmovies.presentation.views.fragments.PopularScreenFragment;
import com.recursia.popularmovies.presentation.views.fragments.SearchScreenFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static final class DetailScreen extends SupportAppScreen {
        private final int movieId;

        public DetailScreen(int movieId) {
            this.movieId = movieId;
        }

        @Override
        public Fragment getFragment() {
            return DetailScreenFragment.getInstance(movieId);
        }
    }

    public static final class FavoriteScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return FavoriteScreenFragment.getInstance();
        }
    }

    public static final class IntroScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return IntroScreenFragment.getInstance();
        }
    }

    public static final class PopularScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return PopularScreenFragment.getInstance();
        }
    }

    public static final class PhotoScreen extends SupportAppScreen {
        private final String imagePath;

        public PhotoScreen(String imagePath) {
            this.imagePath = imagePath;
        }

        @Override
        public Fragment getFragment() {
            return PhotoScreenFragment.getInstance(imagePath);
        }
    }

    public static final class SearchScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return SearchScreenFragment.getInstance();
        }
    }

    public static final class MainScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return MainScreenFragment.getInstance();
        }
    }

}
