package com.example.gopickup.presentation.change_order

import io.reactivex.disposables.CompositeDisposable

class ChangeOrderPresenter(private val view: ChangeOrderContract.View) : ChangeOrderContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {
    }

}