package com.example.gopickup.presentation.history.filter

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView

interface HistoryFilterContract {

    interface View : BaseView {
        fun showStatusList(statusList: List<String>)
    }

    interface Presenter : BasePresenter {
        fun getStatusList(baseRequest: BaseRequest<String>)
    }
}