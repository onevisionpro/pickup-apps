package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class ResendOTPRequest(
    @Json(name = "email")
    val email: String?,
    @Json(name = "hash")
    val hash: String?
)