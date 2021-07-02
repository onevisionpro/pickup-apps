package com.example.gopickup.presentation.home

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.RecentOrder
import com.example.gopickup.model.response.*

interface HomeContract {

    interface View : BaseView {
        fun showVersionChecker(versionChecker: VersionChecker)
        fun showBanners(bannerList: List<Banner>?)
        fun showItems(itemList: List<Item>?)
        fun showRecentOrderItems(recentOrderItems: List<RecentOrderItem>?)
        fun showProfile(profile: Profile?)
    }

    interface Presenter : BasePresenter {
        fun postVersionChecker(baseRequest: BaseRequest<String>)
        fun getHomeInformation(baseRequest: BaseRequest<String>)
        fun getRecentOrderItems(recentOrder: BaseRequest<RecentOrder>)
        fun getProfile(profileRequest: BaseRequest<String>)
    }
}