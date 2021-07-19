package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class Logout(
    @Json(name = "devid")
    val devid: String? = ""
)