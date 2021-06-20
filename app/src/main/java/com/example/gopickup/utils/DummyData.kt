package com.example.gopickup.utils

import com.example.gopickup.R
import com.example.gopickup.model.dummy.Item
import com.example.gopickup.model.dummy.RecentOrder

object DummyData {

    fun generateItems(): List<Item> {
        val itemsHome = mutableListOf<Item>()

        itemsHome.add(Item(image = R.drawable.img_modem_sample, name = "Modem"))
        itemsHome.add(Item(image = R.drawable.img_modem_sample, name = "Modem"))
        itemsHome.add(Item(image = R.drawable.img_modem_sample, name = "Modem"))
        itemsHome.add(Item(image = R.drawable.img_modem_sample, name = "Modem"))
        itemsHome.add(Item(image = R.drawable.img_modem_sample, name = "Modem"))
        itemsHome.add(Item(image = R.drawable.img_modem_sample, name = "Modem"))
        itemsHome.add(Item(image = R.drawable.img_modem_sample, name = "Modem"))

        return itemsHome
    }

    fun generateRecentOrderItems(): List<RecentOrder> {
        val recentOrderItems = mutableListOf<RecentOrder>()

        recentOrderItems.add(
            RecentOrder(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "On Progress - Konfirmasi Mitra",
                date = "6th June",
                status = "On Progress"
            )
        )

        recentOrderItems.add(
            RecentOrder(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "Selesai",
                date = "6th June",
                status = "Selesai"
            )
        )

        recentOrderItems.add(
            RecentOrder(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "Selesai",
                date = "6th June",
                status = "Selesai"
            )
        )

        recentOrderItems.add(
            RecentOrder(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "Selesai",
                date = "6th June",
                status = "Selesai"
            )
        )

        recentOrderItems.add(
            RecentOrder(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "On Progress - Konfirmasi Mitra",
                date = "6th June",
                status = "On Progress"
            )
        )

        recentOrderItems.add(
            RecentOrder(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "On Progress - Konfirmasi Mitra",
                date = "6th June",
                status = "On Progress"
            )
        )

        recentOrderItems.add(
            RecentOrder(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "On Progress - Konfirmasi Mitra",
                date = "6th June",
                status = "On Progress"
            )
        )

        return recentOrderItems
    }
}