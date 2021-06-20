package com.example.gopickup.presentation.home

import com.example.gopickup.model.dummy.Item
import com.example.gopickup.model.dummy.RecentOrder

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

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

    }

}