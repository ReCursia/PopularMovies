package com.recursia.popularmovies.utils.intro

interface AuthPreferences {
    val isAuthorized: Boolean

    fun setAuthorized(value: Boolean)
}
