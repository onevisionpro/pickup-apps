package com.example.gopickup.model.response


import com.squareup.moshi.Json

data class BA(
    @Json(name = "pdf-BA_Pengambilan")
    val pdfBAPengambilan: String? = "",
    @Json(name = "pdf-BA_Pengiriman")
    val pdfBAPengiriman: String? = ""
)