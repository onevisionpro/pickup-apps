package com.example.gopickup.presentation.otp

class OTPPresenter(private val view: OTPContract.View) : OTPContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {
    }

}