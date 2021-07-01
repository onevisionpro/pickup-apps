package com.example.gopickup.presentation.login

import android.util.Log
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.Login
import com.example.gopickup.utils.Constant
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter(
    private val view: LoginContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : LoginContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun postLogin(login: Login) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postLoginAndOTP(login)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showSendOTPSuccess(it.info!!)
                        else -> view.showSendOTPFailed(it.info!!)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constant.DEFAULT_ERROR_MSG)
                    Log.e("LoginPresenter", "ERROR, postLogin: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {

    }
}