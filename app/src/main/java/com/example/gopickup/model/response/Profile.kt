package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class Profile(
    @Json(name = "company_name")
    val companyName: String? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "image_profile")
    val imageProfile: String? = null,
    @Json(name = "msisdn")
    val msisdn: String? = null,
    @Json(name = "nama")
    val nama: String? = null,
    @Json(name = "role")
    val role: String? = null
)