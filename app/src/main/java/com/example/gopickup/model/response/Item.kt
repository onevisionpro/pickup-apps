package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class Item(
    @Json(name = "image")
    val image: String? = null,
    @Json(name = "label")
    val label: String? = null,
    @Json(name = "value")
    val value: String? = null
)