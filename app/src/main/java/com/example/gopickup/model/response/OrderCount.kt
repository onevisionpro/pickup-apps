package com.example.gopickup.model.response

import com.squareup.moshi.Json

data class OrderCount(
    @Json(name = "jumlah_my_order")
    val jumlahMyOrder: Int? = 0,
    @Json(name = "jumlah_open")
    val jumlahOpen: Int? = 0
)