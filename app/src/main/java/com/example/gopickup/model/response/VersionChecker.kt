package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class VersionChecker(
    @Json(name = "push_update")
    val pushUpdate: String?,
    @Json(name = "updated_version")
    val updatedVersion: String?,
    @Json(name = "url_apps")
    val urlApps: String?
)