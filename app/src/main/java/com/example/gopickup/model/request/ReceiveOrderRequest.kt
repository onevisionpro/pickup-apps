package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class ReceiveOrderRequest(
    @Json(name = "image_proven")
    var imageProven: String? = "",
    @Json(name = "track_id")
    var trackId: String? = ""
)