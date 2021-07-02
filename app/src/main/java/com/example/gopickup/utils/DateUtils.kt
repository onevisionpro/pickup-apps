package com.example.gopickup.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    fun toFormatDate(date: String): String {
        val from = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(from.parse(date))
    }
}