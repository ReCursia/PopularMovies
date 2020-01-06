package com.recursia.popularmovies.presentation.ui.navigation;

import android.content.Context;
import android.content.Intent;

import com.recursia.popularmovies.presentation.ui.activities.DetailScreenActivity;
import com.recursia.popularmovies.presentation.ui.activities.FavoriteScreenActivity;
import com.recursia.popularmovies.presentation.ui.activities.IntroScreenActivity;
import com.recursia.popularmovies.presentation.ui.activities.MainScreenActivity;
import com.recursia.popularmovies.presentation.ui.activities.PhotoScreenActivity;
import com.recursia.popularmovies.presentation.ui.activities.SearchScreenActivity;
import com.recursia.popularmovies.utils.TagUtils;

public class Navigator {

    public static void openDetailScreen(Context context, int movieId) {
        Intent intent = new Intent(context, DetailScreenActivity.class);
        intent.putExtra(TagUtils.MOVIE_ID, movieId);
        context.startActivity(intent);
    }

    public static void openFavoriteScreen(Context context) {
        Intent intent = new Intent(context, FavoriteScreenActivity.class);
        context.startActivity(intent);
    }

    public static void openPhotoDetail(Context context, String imagePath) {
        Intent intent = new Intent(context, PhotoScreenActivity.class);
        intent.putExtra(TagUtils.IMAGE_PATH, imagePath);
        context.startActivity(intent);
    }

    public static void openMainScreen(Context context) {
        Intent intent = new Intent(context, MainScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void openIntroActivity(Context context) {
        Intent intent = new Intent(context, IntroScreenActivity.class);
        context.startActivity(intent);
    }

    public static void openSearchScreen(Context context) {
        Intent intent = new Intent(context, SearchScreenActivity.class);
        context.startActivity(intent);
    }

}
