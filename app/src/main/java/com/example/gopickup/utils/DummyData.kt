package com.example.gopickup.utils

import com.example.gopickup.R
import com.example.gopickup.model.dummy.*

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

    fun generateMyOrders(): List<MyOrder> {
        val myOrders = mutableListOf<MyOrder>()

        myOrders.add(
            MyOrder(
                warehouseName = "Warehouse Bogor",
                estimate = "2021/06/03",
                orderId = "123333-123321-12",
                date = "6th July"
            )
        )

        myOrders.add(
            MyOrder(
                warehouseName = "Warehouse Bogor",
                estimate = "2021/06/03",
                orderId = "123333-123321-12",
                date = "6th July"
            )
        )

        myOrders.add(
            MyOrder(
                warehouseName = "Warehouse Bogor",
                estimate = "2021/06/03",
                orderId = "123333-123321-12",
                date = "6th July"
            )
        )

        myOrders.add(
            MyOrder(
                warehouseName = "Warehouse Bogor",
                estimate = "2021/06/03",
                orderId = "123333-123321-12",
                date = "6th July"
            )
        )

        myOrders.add(
            MyOrder(
                warehouseName = "Warehouse Bogor",
                estimate = "2021/06/03",
                orderId = "123333-123321-12",
                date = "6th July"
            )
        )

        myOrders.add(
            MyOrder(
                warehouseName = "Warehouse Bogor",
                estimate = "2021/06/03",
                orderId = "123333-123321-12",
                date = "6th July"
            )
        )

        myOrders.add(
            MyOrder(
                warehouseName = "Warehouse Bogor",
                estimate = "2021/06/03",
                orderId = "123333-123321-12",
                date = "6th July"
            )
        )

        myOrders.add(
            MyOrder(
                warehouseName = "Warehouse Bogor",
                estimate = "2021/06/03",
                orderId = "123333-123321-12",
                date = "6th July"
            )
        )

        return myOrders
    }

    fun generateHistories(): List<History> {
        val histories = mutableListOf<History>()

        histories.add(
            History(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "On Progress - Konfirmasi Mitra",
                date = "6th June",
                status = "On Progress"
            )
        )

        histories.add(
            History(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "Selesai",
                date = "6th June",
                status = "Selesai"
            )
        )

        histories.add(
            History(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "Selesai",
                date = "6th June",
                status = "Selesai"
            )
        )

        histories.add(
            History(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "Selesai",
                date = "6th June",
                status = "Selesai"
            )
        )

        histories.add(
            History(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "On Progress - Konfirmasi Mitra",
                date = "6th June",
                status = "On Progress"
            )
        )

        histories.add(
            History(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "On Progress - Konfirmasi Mitra",
                date = "6th June",
                status = "On Progress"
            )
        )

        histories.add(
            History(
                warehouseName = "Warehouse Bogor",
                orderId = "123123123-123-123",
                statusDesc = "On Progress - Konfirmasi Mitra",
                date = "6th June",
                status = "On Progress"
            )
        )

        return histories
    }

    fun generateDummyProfile(): Profile {
        return Profile(
            name = "Dwiki Firmansyah",
            mitraKerja = "Warehourse Bogor",
            phone = "08123123123",
            email = "faoqi@email.com",
            role = "Warehouse SO",
            confirmPassword = "admin"
        )
    }

}