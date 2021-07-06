package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class EditProfile(
    @Json(name = "email")
    val email: String? = "",
    @Json(name = "msisdn")
    val msisdn: String? = "",
    @Json(name = "name")
    val name: String? = ""
)