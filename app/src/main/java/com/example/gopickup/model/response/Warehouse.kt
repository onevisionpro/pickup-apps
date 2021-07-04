package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class Warehouse(
    @Json(name = "id_warehouse")
    val idWarehouse: String? = "",
    @Json(name = "regional")
    val regional: String? = "",
    @Json(name = "wh_lat")
    val whLat: String? = "",
    @Json(name = "wh_location")
    val whLocation: String? = "",
    @Json(name = "wh_long")
    val whLong: String? = "",
    @Json(name = "wh_name")
    val whName: String? = "",
    @Json(name = "witel")
    val witel: String? = ""
)