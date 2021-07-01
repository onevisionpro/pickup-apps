package com.example.gopickup.presentation.otp

import android.util.Log
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.Login
import com.example.gopickup.utils.Constant
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OTPPresenter(
    private val view: OTPContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : OTPContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun postOTP(login: Login) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postOTP(login)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showOTPSuccess(it.data!!)
                        else -> view.showOTPFailed(it.info!!)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constant.DEFAULT_ERROR_MSG)
                    Log.e("OTPPresenter", "ERROR, postOTP: ${it.localizedMessage}" )
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}