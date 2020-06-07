package com.recursia.popularmovies.utils

object StringUtils {
    fun getCapitalized(str: String) = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase()
}
