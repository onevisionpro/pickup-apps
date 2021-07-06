package com.example.gopickup.presentation.open_order

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.dummy.MyOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.Order

interface OpenOrderContract {

    interface View : BaseView {
        fun showOpenOrderList(openOrderList: List<Order>?)
    }

    interface Presenter : BasePresenter {
        fun getOpenOrderList(trackId: BaseRequest<TrackId>)
    }
}