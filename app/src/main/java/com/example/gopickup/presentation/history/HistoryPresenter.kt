package com.example.gopickup.presentation.history

import com.example.gopickup.model.dummy.History

class HistoryPresenter(private val view: HistoryContract.View) : HistoryContract.Presenter {

    override fun getHistories(historyList: List<History>?) {
        view.showHistories(historyList)
    }

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }
}