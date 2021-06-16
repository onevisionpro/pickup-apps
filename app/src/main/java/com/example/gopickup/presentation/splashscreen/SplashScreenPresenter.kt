package com.example.gopickup.presentation.splashscreen

class SplashScreenPresenter(private val view: SplashScreenContract.View) :
    SplashScreenContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }

}