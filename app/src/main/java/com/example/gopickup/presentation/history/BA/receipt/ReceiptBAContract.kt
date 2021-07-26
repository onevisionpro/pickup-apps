package com.example.gopickup.presentation.history.BA.receipt

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.PreviewBARequest
import com.example.gopickup.model.request.TrackId

interface ReceiptBAContract {

    interface View : BaseView {
        fun showPreviewBA(url: String)
        fun showDownloadBA(url: String)
    }

    interface Presenter : BasePresenter {
        fun getPreviewBA(previewBARequest: BaseRequest<PreviewBARequest>)
        fun getBA(trackId: BaseRequest<TrackId>)
    }
}