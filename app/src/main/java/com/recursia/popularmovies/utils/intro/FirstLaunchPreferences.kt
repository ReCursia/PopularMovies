package com.recursia.popularmovies.utils.intro

interface FirstLaunchPreferences {
    val isFirstLaunch: Boolean

    fun setFirstLaunch(value: Boolean)
}
