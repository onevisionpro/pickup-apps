package com.example.gopickup.presentation.my_orders.details.partner.received_item

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

class MyOrderDetailsReceivedOrderPresenter(
    private val view: MyOrderDetailsReceivedOrderContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : MyOrderDetailsReceivedOrderContract.Presenter {

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

    override fun postReceiveOrder(receiveOrderRequest: BaseRequest<ReceiveOrderRequest>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postReceivedOrder(receiveOrderRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showReceiveOrderSuccess(it.info!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("MyOrderDetailsPresenter", "ERROR, postReceiveOrderSuccess: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}