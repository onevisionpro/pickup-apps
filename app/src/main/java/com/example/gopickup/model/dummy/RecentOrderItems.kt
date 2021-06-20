package com.example.gopickup.model.dummy

data class RecentOrderItems(
    val recentOrderItems: List<RecentOrder>? = null
)

data class RecentOrder(
    val warehouseName: String? = null,
    val orderId: String? = null,
    val statusDesc: String? = null,
    val date: String? = null,
    val status: String? = null
)