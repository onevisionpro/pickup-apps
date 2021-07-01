package com.example.gopickup.presentation.home

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.dummy.Item
import com.example.gopickup.model.dummy.RecentOrder
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.utils.Constant
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
                    view.showMessage(Constant.DEFAULT_ERROR_MSG)
                    Log.e("HomePresenter", "ERROR, postVersionChecker: ${it.localizedMessage}")
                }
            ))
    }

    override fun getItems(items: List<Item>?) {
        view.showItems(items)
    }

    override fun getRecentOrderItems(recentOrderItems: List<RecentOrder>?) {
        view.showRecentOrderItems(recentOrderItems)
    }

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}