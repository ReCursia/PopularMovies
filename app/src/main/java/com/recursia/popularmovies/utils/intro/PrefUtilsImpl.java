package com.recursia.popularmovies.utils.intro;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtilsImpl implements PrefUtils {
    private static final String TAG = "intro";
    private final Context context;

    public PrefUtilsImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean getValue() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(TAG, true);
    }

    @Override
    public void putValue(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(TAG, value).apply();
    }

}
