package com.recursia.popularmovies.ui.navigation;

import android.content.Context;
import android.content.Intent;

import com.recursia.popularmovies.ui.activities.DetailActivity;
import com.recursia.popularmovies.ui.activities.FavoriteActivity;
import com.recursia.popularmovies.ui.activities.IntroActivity;
import com.recursia.popularmovies.ui.activities.MainActivity;
import com.recursia.popularmovies.ui.activities.PhotoActivity;
import com.recursia.popularmovies.ui.activities.SearchActivity;
import com.recursia.popularmovies.utils.TagUtils;

public class Navigator {

    public static void openDetailScreen(Context context, int movieId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(TagUtils.MOVIE_ID, movieId);
        context.startActivity(intent);
    }

    public static void openFavoriteScreen(Context context) {
        Intent intent = new Intent(context, FavoriteActivity.class);
        context.startActivity(intent);
    }

    public static void openPhotoDetail(Context context, String imagePath) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(TagUtils.IMAGE_PATH, imagePath);
        context.startActivity(intent);
    }

    public static void openMainScreen(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void openIntroActivity(Context context) {
        Intent intent = new Intent(context, IntroActivity.class);
        context.startActivity(intent);
    }

    public static void openSearchScreen(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

}
