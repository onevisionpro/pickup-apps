package com.example.gopickup.presentation.history.BA.shipment

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.PreviewBARequest
import com.example.gopickup.model.request.TrackId

interface ShipmentBAContract {

    interface View : BaseView {
        fun showGeneratedBA(url: String)
        fun showNoBA(message: String)
    }

    interface Presenter : BasePresenter {
        fun getGeneratedBA(trackId: BaseRequest<TrackId>)
    }
}