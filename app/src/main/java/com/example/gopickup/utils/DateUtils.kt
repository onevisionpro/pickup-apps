package com.example.gopickup.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    fun toFormatDate(date: String): String {
        val from = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(from.parse(date))
    }

    fun formatDate(cal: Date): String {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(cal.time)
    }
}