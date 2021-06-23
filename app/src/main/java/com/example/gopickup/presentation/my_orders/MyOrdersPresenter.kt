package com.example.gopickup.presentation.my_orders

import com.example.gopickup.model.dummy.MyOrder

class MyOrdersPresenter(private val view: MyOrdersContract.View) : MyOrdersContract.Presenter {
    override fun getMyOrderList(myOrderList: List<MyOrder>) {
        view.showMyOrderList(myOrderList)
    }

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }
}