package com.example.gopickup.presentation.my_orders.details.partner.ba_details

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.SendOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SubmitBATakeOrderPresenter(
    private val view: SubmitBATakeOrderContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : SubmitBATakeOrderContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun getPreviewBA(trackId: BaseRequest<TrackId>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getPreviewBA(trackId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showPreviewBA(it.data!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("SubmitBATakeOrderPresenter", "ERROR, getPreviewBA: ${it.localizedMessage}")
                }
            ))
    }

    override fun postSendOrder(sendOrder: BaseRequest<SendOrder>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postSendOrder(sendOrder)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showSendOrderSuccess(it.info!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("SubmitBATakeOrderPresenter", "ERROR, postSendOrder: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}