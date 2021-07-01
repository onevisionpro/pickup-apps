package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class User(
    @Json(name = "gender")
    val gender: String?,
    @Json(name = "jabatan")
    val jabatan: String?,
    @Json(name = "nama")
    val nama: String?,
    @Json(name = "no_hp")
    val noHp: String?,
    @Json(name = "regional")
    val regional: Any?,
    @Json(name = "role")
    val role: String?,
    @Json(name = "token")
    val token: String?,
    @Json(name = "username")
    val username: String?,
    @Json(name = "witel")
    val witel: String?
)