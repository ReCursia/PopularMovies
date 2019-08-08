package com.example.popularmovies.utils;

import java.util.Locale;

public class NetworkUtils {
    public static final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";
    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";
    //Sorting
    public static final String TOP_RATED = "vote_average.desc";
    public static final String POPULARITY = "popularity.desc";

    public static String getDefaultLanguage() {
        return Locale.getDefault().toString();
    }
}
