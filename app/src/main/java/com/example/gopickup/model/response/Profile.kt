package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class Profile(
    @Json(name = "company_name")
    val companyName: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "image_profile")
    val imageProfile: String?,
    @Json(name = "msisdn")
    val msisdn: String?,
    @Json(name = "nama")
    val nama: String?
)