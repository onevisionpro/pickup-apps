package com.example.gopickup.presentation.my_orders.details.partner.ba_details

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.SendOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.PreviewBA

interface SubmitBATakeOrderContract {

    interface View : BaseView {
        fun showPreviewBA(previewBA: PreviewBA)
        fun showSendOrderSuccess(message: String)
    }

    interface Presenter : BasePresenter {
        fun getPreviewBA(trackId: BaseRequest<TrackId>)
        fun postSendOrder(sendOrder: BaseRequest<SendOrder>)
    }

}