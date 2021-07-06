package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class OrderId(
    @Json(name = "order_id")
    val orderId: String? = ""
)