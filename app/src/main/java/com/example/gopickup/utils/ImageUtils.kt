package com.example.gopickup.utils

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream


object ImageUtils {

    fun toBase64(bitmap: Bitmap): String? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }
}