package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class HistoryOrderDetails(
    @Json(name = "create_by")
    val createBy: String? = "",
    @Json(name = "create_dtm")
    val createDtm: String? = "",
    @Json(name = "items")
    val items: List<ItemOrder>? = listOf(),
    @Json(name = "notes")
    val notes: String? = "",
    @Json(name = "order_from")
    val orderFrom: String? = "",
    @Json(name = "order_to")
    val orderTo: String? = "",
    @Json(name = "progress_order")
    val progressOrder: List<ProgressOrder>? = listOf(),
    @Json(name = "resi_code")
    val resiCode: String? = "",
    @Json(name = "status")
    val status: String? = "",
    @Json(name = "track_id")
    val trackId: String? = ""
)

data class ItemOrder(
    @Json(name = "item_name")
    val itemName: String? = "",
    @Json(name = "jumlah")
    val jumlah: String? = ""
)

data class ProgressOrder(
    @Json(name = "processed_by")
    val processedBy: String? = "",
    @Json(name = "processed_dtm")
    val processedDtm: String? = "",
    @Json(name = "status")
    val status: String? = ""
)