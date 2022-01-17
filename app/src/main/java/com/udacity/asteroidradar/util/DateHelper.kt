package com.udacity.asteroidradar.util

import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getTodayAsFormattedString() : String {
        val dateFormatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance(Locale.getDefault())
        val date = dateFormatter.format(calendar.time)

        return date.toString()
    }
}