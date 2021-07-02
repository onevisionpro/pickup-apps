package com.example.gopickup.presentation.history.details

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HistoryDetailsPresenter(
    private val view: HistoryDetailsContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : HistoryDetailsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun getHistoryOrderDetails(trackId: BaseRequest<TrackId>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getHistoryOrderDetails(trackId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showHistoryOrderDetails(it.data!![0])
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("HistoryDetailsPresenter", "ERROR, getHistoryOrderDetails: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }


}