package com.example.gopickup.presentation.home

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.RecentOrder
import com.example.gopickup.model.response.Banner
import com.example.gopickup.model.response.Item
import com.example.gopickup.model.response.RecentOrderItem
import com.example.gopickup.model.response.VersionChecker

interface HomeContract {

    interface View : BaseView {
        fun showVersionChecker(versionChecker: VersionChecker)
        fun showBanners(bannerList: List<Banner>?)
        fun showItems(itemList: List<Item>?)
        fun showRecentOrderItems(recentOrderItems: List<RecentOrderItem>?)
    }

    interface Presenter : BasePresenter {
        fun postVersionChecker(baseRequest: BaseRequest<String>)
        fun getHomeInformation(baseRequest: BaseRequest<String>)
        fun getRecentOrderItems(recentOrder: BaseRequest<RecentOrder>)
    }
}