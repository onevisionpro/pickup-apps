package com.example.gopickup.presentation.my_orders.details.partner

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.TakeOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.OrderDetails

interface MyOrderDetailsContract {

    interface View : BaseView {
        fun showMyOrderDetails(orderDetails: OrderDetails)
        fun showTakeOrderSuccess(message: String)
    }

    interface Presenter : BasePresenter {
        fun getMyOrderDetails(trackId: BaseRequest<TrackId>)
        fun postTakeOrder(takeOrder: BaseRequest<TakeOrder>)
    }
}