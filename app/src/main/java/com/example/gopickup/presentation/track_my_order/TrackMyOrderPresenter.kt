package com.example.gopickup.presentation.track_my_order

class TrackMyOrderPresenter(private val view: TrackMyOrderContract.View) :
    TrackMyOrderContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }

}