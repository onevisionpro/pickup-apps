package com.example.gopickup.presentation.history

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.dummy.History
import com.example.gopickup.model.dummy.RecentOrder

interface HistoryContract {

    interface View : BaseView {
        fun showHistories(historyList: List<History>?)
    }

    interface Presenter : BasePresenter {
        fun getHistories(historyList: List<History>?)
    }
}