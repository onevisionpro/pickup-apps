package com.example.gopickup.presentation.my_orders.details.warehouse

class MyOrderDetailsPresenter(private val view: MyOrderDetailsContract.View)
    : MyOrderDetailsContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }

}