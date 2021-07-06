package com.example.gopickup.presentation.open_order.details.book_order

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OpenOrderDetailsPresenter(
    private val view: OpenOrderDetailsContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : OpenOrderDetailsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun getOpenOrderDetails(trackId: BaseRequest<TrackId>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getOpenOrderDetails(trackId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showOpenOrderDetails(it.data?.get(0)!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("OpenOrderDetailsPresenter", "ERROR, getOpenOrderDetails: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}