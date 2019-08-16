package com.example.popularmovies.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(String dateString) {
        SimpleDateFormat sdfOriginal = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdfResult = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        try {
            Date date = sdfOriginal.parse(dateString);
            return sdfResult.format(date);
        } catch (ParseException err) {
            return dateString;
        }
    }

    public static String formatTime(int minutes) {
        return String.format(Locale.getDefault(), "%d hrs %d mins", (minutes / 60) % 60, minutes % 60);
    }

}
