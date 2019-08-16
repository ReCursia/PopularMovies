package com.example.popularmovies.utils;

import java.util.Locale;

public class NetworkUtils {
    public static final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";
    public static final String TRAILER_IMAGE_FORMAT_URL = "http://img.youtube.com/vi/%s/mqdefault.jpg"; //returns 320x180 image
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final String BIG_POSTER_SIZE = "w780";
    public static final String MEDIUM_PROFILE_SIZE = "w185";
    public static final int VOTE_COUNT_TOP_RATED = 2000;
    public static final int VOTE_COUNT_POPULARITY = 0; //doesn't matter
    //Sorting
    public static final String TOP_RATED = "vote_average.desc";
    public static final String POPULARITY = "popularity.desc";
    //Google play store
    public static final String GOOGLE_PLAY_NATIVE = "market://details?id=";
    public static final String GOOGLE_PLAY_URL = "http://play.google.com/store/apps/details?id=";

    public static String getDefaultLanguage() {
        return Locale.getDefault().toString();
    }
}
