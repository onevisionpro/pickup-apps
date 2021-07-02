package com.example.gopickup.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {
    @JvmStatic
    fun toSimpleString(date: Date) : String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun toFormatDate(date: String): String {
        val from = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(from.parse(date))
    }
}