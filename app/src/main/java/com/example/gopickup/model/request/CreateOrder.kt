package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class CreateOrder(
    @Json(name = "arrival_date")
    var arrivalDate: String? = "",
    @Json(name = "id_warehouse")
    var idWarehouse: String? = "",
    @Json(name = "items")
    var items: List<Item>? = listOf()
)

data class Item(
    @Json(name = "id_item")
    var idItem: String? = "",
    @Json(name = "jumlah")
    var jumlah: String? = ""
)