package com.example.gopickup.presentation.history.BA.receipt

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.PreviewBARequest
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReceiptBAPresenter(
    private val view: ReceiptBAContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : ReceiptBAContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getGeneratedBA(trackId: BaseRequest<TrackId>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postGenerateBA(trackId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showGeneratedBA(it.data?.pdfBAPengambilan!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        StatusCode.NO_BA -> view.showNoBA(it.info!!)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("ReceiptBAPresenter", "ERROR, getBA: ${it.localizedMessage}")
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