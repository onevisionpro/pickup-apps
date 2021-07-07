package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class TakeOrder(
    @Json(name = "notes")
    val notes: String? = "",
    @Json(name = "resi_code")
    val resiCode: String? = "",
    @Json(name = "track_id")
    val trackId: String? = ""
)