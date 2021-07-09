package com.example.gopickup.presentation.my_orders.filter

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView

interface MyOrderFilterContract {

    interface View : BaseView {
        fun showStatusList(statusList: List<String>)
    }

    interface Presenter : BasePresenter {
        fun getStatusList(baseRequest: BaseRequest<String>)
    }
}