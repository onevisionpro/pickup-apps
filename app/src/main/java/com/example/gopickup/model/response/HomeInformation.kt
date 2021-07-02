package com.example.gopickup.model.response

import com.squareup.moshi.Json

data class HomeInformation(
    @Json(name = "home_banner")
    val homeBannerList: List<Banner>? = null,
    @Json(name = "item")
    val itemList: List<Item>? = null
)