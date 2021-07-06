package com.example.gopickup.presentation.open_order.details.book_order

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.OrderDetails

interface OpenOrderDetailsContract {

    interface View : BaseView {
        fun showOpenOrderDetails(orderDetails: OrderDetails)
        fun showBookOrderSuccess(message: String)
    }

    interface Presenter : BasePresenter {
        fun getOpenOrderDetails(trackId: BaseRequest<TrackId>)
        fun postBookOrder(trackId: BaseRequest<TrackId>)
    }
}