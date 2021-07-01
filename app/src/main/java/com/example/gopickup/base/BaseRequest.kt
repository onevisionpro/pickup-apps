package com.example.gopickup.base

import com.squareup.moshi.Json
import kotlin.Int

data class BaseRequest<T>(
    @Json(name = "guid")
    val guid: String? = "",
    @Json(name = "code")
    val code: String? = "",
    @Json(name = "data")
    var data: T? = null
)