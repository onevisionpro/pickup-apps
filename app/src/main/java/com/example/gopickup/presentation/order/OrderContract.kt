package com.example.gopickup.presentation.order

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView

interface OrderContract {

    interface View : BaseView {
        fun showMyOrderCount(count: Int)
        fun showOpenOrderCount(count: Int)
    }

    interface Presenter : BasePresenter {
        fun getOrderCount(baseRequest: BaseRequest<String>)
    }
}