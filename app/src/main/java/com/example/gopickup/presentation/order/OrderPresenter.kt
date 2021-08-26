package com.example.gopickup.presentation.order

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepository
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrderPresenter(
    private val view: OrderContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : OrderContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getOrderCount(baseRequest: BaseRequest<String>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getOrderCount(baseRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> {
                            view.showMyOrderCount(it.data?.jumlahMyOrder!!)
                            view.showOpenOrderCount(it.data.jumlahOpen!!)
                        }
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("OrderPresenter", "ERROR, getOrderCount: ${it.localizedMessage}")
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