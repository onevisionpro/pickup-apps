package com.example.gopickup.presentation.login

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.response.User

interface LoginContract {

    interface View : BaseView {
        fun showLoginSuccessForWarehouse(baseResponse: BaseResponse<User>)
        fun showSendOTPSuccess(message: String)
        fun showSendOTPFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun postLogin(login: Login)
    }
}