package com.example.gopickup.base

import com.squareup.moshi.Json
import kotlin.Int

data class BaseResponse<T>(
    @Json(name = "code")
    val code: String? = null,
    @Json(name = "info")
    val info: String? = null,
    @Json(name = "data")
    val data: T? = null
)