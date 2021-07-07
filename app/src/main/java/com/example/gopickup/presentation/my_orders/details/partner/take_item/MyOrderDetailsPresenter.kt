package com.example.gopickup.presentation.my_orders.details.partner.take_item

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.ReceiveOrderRequest
import com.example.gopickup.model.request.TakeOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MyOrderDetailsPresenter(
    private val view: MyOrderDetailsContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : MyOrderDetailsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun getMyOrderDetails(trackId: BaseRequest<TrackId>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getOrderDetails(trackId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showMyOrderDetails(it.data?.get(0)!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("MyOrderDetailsPresenter", "ERROR, getMyOrderDetails: ${it.localizedMessage}")
                }
            ))
    }

    override fun postTakeOrder(takeOrder: BaseRequest<TakeOrder>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postTakeOrder(takeOrder)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showTakeOrderSuccess(it.info!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("MyOrderDetailsPresenter", "ERROR, postTakeOrder: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}