package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class TrackId(
    @Json(name = "track_id")
    val trackId: String? = ""
)