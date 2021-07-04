package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class ItemWarehouse(
    @Json(name = "create_dtm")
    val createDtm: String? = "",
    @Json(name = "id_item")
    val idItem: String? = "",
    @Json(name = "item_desc")
    val itemDesc: String? = "",
    @Json(name = "item_name")
    val itemName: String? = "",
    @Json(name = "status")
    val status: String? = "",
    @Json(name = "update_dtm")
    val updateDtm: String? = ""
)