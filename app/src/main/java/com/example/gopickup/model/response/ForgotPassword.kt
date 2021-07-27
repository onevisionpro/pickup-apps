package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class ForgotPassword(
    @Json(name = "html")
    val html: String? = "",
    @Json(name = "wording")
    val wording: String? = ""
)