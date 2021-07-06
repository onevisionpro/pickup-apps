package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class Type(
    @Json(name = "type")
    val type: String? = ""
)