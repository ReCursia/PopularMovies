package com.recursia.popularmovies.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatDate(dateString: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val resultFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        return try {
            val date = originalFormat.parse(dateString)
            resultFormat.format(date)
        } catch (err: ParseException) {
            dateString
        }
    }
}
