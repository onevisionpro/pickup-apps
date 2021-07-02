package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class Banner(
    @Json(name = "image")
    val image: String? = null,
    @Json(name = "label")
    val label: String? = null
)