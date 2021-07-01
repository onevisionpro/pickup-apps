package com.example.gopickup.presentation.detail_order_partner

class DetailOrderPartnerPresenter(private val view: DetailOrderPartnerContract.View)
    : DetailOrderPartnerContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }

}