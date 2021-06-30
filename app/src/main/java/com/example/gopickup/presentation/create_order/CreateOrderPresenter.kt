package com.example.gopickup.presentation.create_order

class CreateOrderPresenter(private val view: CreateOrderContract.View) :
    CreateOrderContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {
    }

}