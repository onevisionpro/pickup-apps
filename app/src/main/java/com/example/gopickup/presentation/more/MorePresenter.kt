package com.example.gopickup.presentation.more

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.Logout
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MorePresenter(
    private val view: MoreContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : MoreContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun postLogout(logout: BaseRequest<Logout>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postLogout(logout)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showLogoutSuccess(it.info!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("MorePresenter", "ERROR, postLogout: ${it.localizedMessage}")
                }
            ))
    }

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}