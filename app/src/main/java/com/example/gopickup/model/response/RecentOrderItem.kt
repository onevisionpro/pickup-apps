package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class RecentOrderItem(
    @Json(name = "create_by")
    val createBy: String? = null,
    @Json(name = "create_dtm")
    val createDtm: String? = null,
    @Json(name = "order_from")
    val orderFrom: String? = null,
    @Json(name = "order_to")
    val orderTo: String? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "track_id")
    val trackId: String? = null
)