package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class EditOrder(
    @Json(name = "track_id")
    var track_id: String? = "",
    @Json(name = "arrival_date")
    var arrivalDate: String? = "",
    @Json(name = "id_warehouse")
    var idWarehouse: String? = "",
    @Json(name = "items")
    var items: List<Item>? = listOf()
)