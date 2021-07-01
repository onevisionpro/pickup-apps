package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class Login(
    @Json(name = "guid")
    val guid: String? = "",
    @Json(name = "code")
    val code: String? = "",
    @Json(name = "data")
    val data: Data? = null
)

data class Data(
    @Json(name = "devid")
    val devid: String? = "",
    @Json(name = "email")
    val email: String? = "",
    @Json(name = "otp")
    val otp: String? = "",
    @Json(name = "password")
    val password: String? = ""
)