package com.example.gopickup.utils

import java.math.BigInteger
import java.security.MessageDigest

object StringUtils {

    fun toMd5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}