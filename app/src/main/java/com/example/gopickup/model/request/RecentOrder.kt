package com.example.gopickup.model.request

import com.squareup.moshi.Json

data class RecentOrder(
    @Json(name = "limit")
    val limit: String? = null
)