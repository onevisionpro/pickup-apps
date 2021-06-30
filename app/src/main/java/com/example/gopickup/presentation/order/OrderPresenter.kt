package com.example.gopickup.presentation.order

class OrderPresenter(private val view: OrderContract.View) : OrderContract.Presenter {
    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }
}