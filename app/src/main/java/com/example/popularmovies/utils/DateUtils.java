package com.example.popularmovies.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(String dateString) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat resultFormat = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        try {
            Date date = originalFormat.parse(dateString);
            return resultFormat.format(date);
        } catch (ParseException err) {
            return dateString;
        }
    }
}
