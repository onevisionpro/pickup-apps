package com.example.gopickup.presentation.home

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.RecentOrder
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(
    private val view: HomeContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : HomeContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun postVersionChecker(baseRequest: BaseRequest<String>) {
        compositeDisposable.add(appRepositoryImpl.postVersionChecker(baseRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showVersionChecker(it.data!!)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("HomePresenter", "ERROR, postVersionChecker: ${it.localizedMessage}")
                }
            ))
    }

    override fun getHomeInformation(baseRequest: BaseRequest<String>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getHomeInformation(baseRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> {
                            view.showBanners(it.data?.homeBannerList)
                            view.showItems(it.data?.itemList)
                        }
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("HomePresenter", "ERROR, getHomeInformation: ${it.localizedMessage}")
                }
            ))
    }

    override fun getRecentOrderItems(recentOrder: BaseRequest<RecentOrder>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getRecentOrderItems(recentOrder)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showRecentOrderItems(it.data)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("HomePresenter", "ERROR, getHomeInformation: ${it.localizedMessage}")
                }
            ))
    }

    override fun getProfile(profileRequest: BaseRequest<String>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getProfile(profileRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showProfile(it.data)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("HomePresenter", "ERROR, getProfile: ${it.localizedMessage}")
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