package com.recursia.popularmovies.utils.intro

import android.content.Context
import android.preference.PreferenceManager

class PreferencesImpl(private val context: Context) : AuthPreferences, FirstLaunchPreferences {
    override val isAuthorized: Boolean
        get() {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(AUTH_TAG, false)
        }

    override fun setAuthorized(value: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean(AUTH_TAG, value).apply()
    }

    override val isFirstLaunch: Boolean
        get() {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(FIRST_LAUNCH_TAG, true)
        }

    override fun setFirstLaunch(value: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean(FIRST_LAUNCH_TAG, value).apply()
    }

    companion object {
        private const val AUTH_TAG = "auth_tag"
        private const val FIRST_LAUNCH_TAG = "first_launch_tag"
    }
}
