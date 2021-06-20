package com.example.gopickup.model.dummy

data class ItemsHome(
    val items: List<Item>? = null
)

data class Item(
    val image: Int? = null,
    val name: String? = null
)