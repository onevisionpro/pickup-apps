package com.example.gopickup.presentation.open_order.details

class OpenOrderDetailsPresenter(private val view: OpenOrderDetailsContract.View)
    : OpenOrderDetailsContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }

}