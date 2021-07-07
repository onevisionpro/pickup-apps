package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class PreviewBARequest(
    @Json(name = "track_id")
    val trackId: String? = "",
    @Json(name = "type")
    val type: String? = ""
)