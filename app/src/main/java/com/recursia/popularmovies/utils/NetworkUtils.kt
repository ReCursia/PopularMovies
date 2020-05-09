package com.recursia.popularmovies.utils

object NetworkUtils {
    const val TRAILER_BASE_URL = "https://www.youtube.com/watch?v="
    const val TRAILER_IMAGE_FORMAT_URL = "http://img.youtube.com/vi/%s/mqdefault.jpg" // returns 320x180 image
    const val VOTE_COUNT_TOP_RATED = 2000
    const val VOTE_COUNT_POPULARITY = 0 // doesn't matter
    // Sorting
    const val TOP_RATED = "vote_average.desc"
    const val POPULARITY = "popularity.desc"
    // Google play store
    const val GOOGLE_PLAY_NATIVE = "market://details?id="
    const val GOOGLE_PLAY_URL = "http://play.google.com/store/apps/details?id="
    private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
    private const val MEDIUM_POSTER_SIZE = "w500"
    private const val BIG_POSTER_SIZE = "w780"
    private const val ORIGINAL_SIZE = "original"
    private const val MEDIUM_PROFILE_SIZE = "w185"

    fun getBigPosterUrl(imagePath: String): String {
        return BASE_IMAGE_URL + BIG_POSTER_SIZE + imagePath
    }

    fun getOriginalPosterUrl(imagePath: String): String {
        return BASE_IMAGE_URL + ORIGINAL_SIZE + imagePath
    }

    fun getMediumProfileUrl(imagePath: String): String {
        return BASE_IMAGE_URL + MEDIUM_PROFILE_SIZE + imagePath
    }

    fun getMediumPosterUrl(imagePath: String): String {
        return BASE_IMAGE_URL + MEDIUM_POSTER_SIZE + imagePath
    }
}
