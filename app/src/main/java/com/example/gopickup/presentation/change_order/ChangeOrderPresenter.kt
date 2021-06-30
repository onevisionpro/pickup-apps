package com.example.gopickup.presentation.change_order

class ChangeOrderPresenter(private val view: ChangeOrderContract.View) :
    ChangeOrderContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {
    }

}