package com.example.gopickup.model.request


import com.squareup.moshi.Json

data class ResetPassword(
    @Json(name = "new_password")
    val newPassword: String? = "",
    @Json(name = "new_password_conf")
    val newPasswordConf: String? = ""
)