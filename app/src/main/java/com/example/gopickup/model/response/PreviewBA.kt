package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class PreviewBA(
    @Json(name = "content_html")
    val contentHtml: String? = "",
    @Json(name = "pdf_icon")
    val pdf_icon: String? = ""
)