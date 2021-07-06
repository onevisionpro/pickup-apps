package com.example.gopickup.presentation.about_apps

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.Type
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AboutAppsPresenter(
    private val view: AboutAppsContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : AboutAppsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun getWordings(moreType: BaseRequest<Type>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getWordings(moreType)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showWordings(it.data?.get(0)!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("AboutAppsPresenter", "ERROR, getWordings: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}