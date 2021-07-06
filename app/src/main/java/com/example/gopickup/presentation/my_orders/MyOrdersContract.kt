package com.example.gopickup.presentation.my_orders

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.Order

interface MyOrdersContract {

    interface View : BaseView {
        fun showMyOrderList(myOrderList: List<Order>?)
    }

    interface Presenter : BasePresenter {
        fun getMyOrderList(trackId: BaseRequest<TrackId>)
    }
}