package com.example.gopickup.presentation.open_order

import com.example.gopickup.model.dummy.MyOrder

class OpenOrderPresenter(private val view: OpenOrderContract.View) : OpenOrderContract.Presenter {
    override fun getOpenOrders(openOrderList: List<MyOrder>) {
        view.showOpenOrders(openOrderList)
    }

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }
}