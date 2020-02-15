package com.recursia.popularmovies.utils.intro

import android.content.Context
import android.preference.PreferenceManager

class PrefUtilsImpl(private val context: Context) : PrefUtils {

    override val value: Boolean
        get() {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(TAG, true)
        }

    override fun putValue(value: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean(TAG, value).apply()
    }

    companion object {
        private const val TAG = "intro"
    }

}
