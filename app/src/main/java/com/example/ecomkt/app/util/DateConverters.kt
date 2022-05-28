package com.example.ecomkt.app.util

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateConverters {

    val FORMATTER = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

    @TypeConverter
    internal fun fromTimeStamp(dateLong: Long): String {
        return FORMATTER.format(dateLong)
    }

    @TypeConverter
    internal fun dateToTimeStamp(date: String): Long {
        var timeStamp: Long? = null
        try {
            timeStamp = FORMATTER.parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeStamp!!
    }
}