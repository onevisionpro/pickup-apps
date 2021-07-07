package com.example.gopickup.presentation.history

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.dummy.History
import com.example.gopickup.model.dummy.RecentOrder
import com.example.gopickup.model.request.HistoryOrderRequest
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.HistoryOrder

interface HistoryContract {

    interface View : BaseView {
        fun showHistoriesOrder(historyOrderList: List<HistoryOrder>?)
    }

    interface Presenter : BasePresenter {
        fun getHistoriesOrder(historyOrderRequest: BaseRequest<HistoryOrderRequest>)
    }
}