package com.example.gopickup.presentation.my_orders.details.warehouse

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.TakeOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.OrderDetails

interface MyOrderDetailsWarehouseContract {

    interface View : BaseView {
        fun showMyOrderDetails(orderDetails: OrderDetails)
        fun showOrderArrivedSuccess(message: String)
    }

    interface Presenter : BasePresenter {
        fun getMyOrderDetails(trackId: BaseRequest<TrackId>)
        fun postOrderArrived(trackId: BaseRequest<TrackId>)
    }
}