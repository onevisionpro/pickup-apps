package com.example.gopickup.presentation.my_orders

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.dummy.MyOrder

interface MyOrdersContract {

    interface View : BaseView {
        fun showMyOrderList(myOrderList: List<MyOrder>?)
    }

    interface Presenter : BasePresenter {
        fun getMyOrderList(myOrderList: List<MyOrder>)
    }
}