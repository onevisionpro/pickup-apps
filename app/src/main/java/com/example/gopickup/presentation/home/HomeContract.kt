package com.example.gopickup.presentation.home

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.dummy.Item
import com.example.gopickup.model.dummy.RecentOrder

interface HomeContract {

    interface View : BaseView {
        fun showItems(items: List<Item>?)
        fun showRecentOrderItems(recentOrderItems: List<RecentOrder>?)
    }

    interface Presenter : BasePresenter {
        fun getItems(items: List<Item>?)
        fun getRecentOrderItems(recentOrderItems: List<RecentOrder>?)
    }
}