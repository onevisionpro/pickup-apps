package com.example.gopickup.presentation.open_order

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.dummy.MyOrder

interface OpenOrderContract {

    interface View : BaseView {
        fun showOpenOrders(openOrderList: List<MyOrder>?)
    }

    interface Presenter : BasePresenter {
        fun getOpenOrders(openOrderList: List<MyOrder>)
    }
}