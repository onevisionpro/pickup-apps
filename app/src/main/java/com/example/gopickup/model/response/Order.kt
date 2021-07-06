package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class Order(
    @Json(name = "create_by")
    val createBy: String? = "",
    @Json(name = "create_dtm")
    val createDtm: String? = "",
    @Json(name = "estimate_arrival")
    val estimateArrival: String? = "",
    @Json(name = "order_from")
    val orderFrom: String? = "",
    @Json(name = "order_to")
    val orderTo: String? = "",
    @Json(name = "status")
    val status: String? = "",
    @Json(name = "track_id")
    val trackId: String? = ""
)