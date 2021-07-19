package com.example.gopickup.presentation.more

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.Logout

interface MoreContract {

    interface View : BaseView {
        fun showLogoutSuccess(message: String)
    }

    interface Presenter : BasePresenter {
        fun postLogout(logout: BaseRequest<Logout>)
    }
}