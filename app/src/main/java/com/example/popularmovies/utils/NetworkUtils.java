package com.example.popularmovies.utils;

import java.util.Locale;

public class NetworkUtils {
    public static final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";
    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String BIG_POSTER_SIZE = "w780";
    public static final int VOTE_COUNT_TOP_RATED = 5000;
    public static final int VOTE_COUNT_POPULARITY = 0; //doesnt matter
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
