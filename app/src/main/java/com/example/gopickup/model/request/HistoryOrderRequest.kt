package com.example.gopickup.model.request


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryOrderRequest(
    @Json(name = "end_dtm")
    var endDtm: String? = "",
    @Json(name = "start_dtm")
    var startDtm: String? = "",
    @Json(name = "status")
    var status: String? = "",
    @Json(name = "track_id")
    var trackId: String? = ""
): Parcelable