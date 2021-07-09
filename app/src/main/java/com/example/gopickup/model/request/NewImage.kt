package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class NewImage(
    @Json(name = "new_image")
    val newImage: String? = ""
)