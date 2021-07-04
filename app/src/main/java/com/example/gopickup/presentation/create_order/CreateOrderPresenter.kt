package com.example.gopickup.presentation.create_order

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.CreateOrder
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CreateOrderPresenter(
    private val view: CreateOrderContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : CreateOrderContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun getWarehouseList(baseRequest: BaseRequest<String>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getWarehouseList(baseRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showWarehouseList(it.data)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("ChangeOrderPresenter", "ERROR, getWarehouseList: ${it.localizedMessage}")
                }
            ))
    }

    override fun getItemList(baseRequest: BaseRequest<String>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getItemListWarehouse(baseRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showItemList(it.data)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("ChangeOrderPresenter", "ERROR, getItemList: ${it.localizedMessage}")
                }
            ))
    }

    override fun postCreateOrder(createOrder: BaseRequest<CreateOrder>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postCreateOrder(createOrder)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showCreateOrderSuccess(it.data?.trackId!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("CreateOrderPresenter", "ERROR, postCreateOrder: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}