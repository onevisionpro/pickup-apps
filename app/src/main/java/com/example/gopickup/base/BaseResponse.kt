package com.example.gopickup.base

import com.squareup.moshi.Json
import kotlin.Int

data class BaseResponse<T>(
    @Json(name = "code")
    val code: Int? = null,
    @Json(name = "data")
    val data: T? = null,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "success")
    val success: Boolean? = null
)