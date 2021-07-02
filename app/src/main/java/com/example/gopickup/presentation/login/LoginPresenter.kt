package com.example.gopickup.presentation.login

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.Login
import com.example.gopickup.utils.Constants
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

    override fun postVersionChecker(baseRequest: BaseRequest<String>) {
        compositeDisposable.add(appRepositoryImpl.postVersionChecker(baseRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showVersionChecker(it.data!!)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("LoginPresenter", "ERROR, postVersionChecker: ${it.localizedMessage}")
                }
            ))
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
                        StatusCode.SUCCESS_LOGIN_PARTNER -> view.showSendOTPSuccess(it.info!!)
                        StatusCode.SUCCESS_LOGIN_WAREHOUSE -> view.showLoginSuccessForWarehouse(it)
                        else -> view.showSendOTPFailed(it.info!!)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("LoginPresenter", "ERROR, postLogin: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}