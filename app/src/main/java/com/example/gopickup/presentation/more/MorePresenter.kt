package com.example.gopickup.presentation.more

class MorePresenter(private val view: MoreContract.View) : MoreContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }

}