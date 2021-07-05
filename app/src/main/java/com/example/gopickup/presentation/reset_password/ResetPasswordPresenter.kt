package com.example.gopickup.presentation.reset_password

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.ResetPassword
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ResetPasswordPresenter(
    private val view: ResetPasswordContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : ResetPasswordContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun postResetPassword(resetPassword: BaseRequest<ResetPassword>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postResetPassword(resetPassword)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showResetPasswordSuccess(it.info!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("ResetPasswordPresenter", "ERROR, postResetPassword: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}