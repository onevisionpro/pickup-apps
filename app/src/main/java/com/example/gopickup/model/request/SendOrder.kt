package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class SendOrder(
    @Json(name = "track_id")
    val trackId: String? = "",
    @Json(name = "ttd_mitra")
    val ttdMitra: String? = "",
    @Json(name = "ttd_warehouse")
    val ttdWarehouse: String? = ""
)