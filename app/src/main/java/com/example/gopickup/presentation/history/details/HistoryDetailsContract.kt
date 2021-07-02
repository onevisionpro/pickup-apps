package com.example.gopickup.presentation.history.details

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.HistoryOrderDetails

interface HistoryDetailsContract {

    interface View : BaseView {
        fun showHistoryOrderDetails(details: HistoryOrderDetails)
    }

    interface Presenter : BasePresenter {
        fun getHistoryOrderDetails(trackId: BaseRequest<TrackId>)
    }
}