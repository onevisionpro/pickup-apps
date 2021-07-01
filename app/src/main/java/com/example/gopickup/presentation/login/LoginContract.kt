package com.example.gopickup.presentation.login

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.response.User
import com.example.gopickup.model.response.VersionChecker

interface LoginContract {

    interface View : BaseView {
        fun showVersionChecker(versionChecker: VersionChecker)
        fun showLoginSuccessForWarehouse(baseResponse: BaseResponse<User>)
        fun showSendOTPSuccess(message: String)
        fun showSendOTPFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun postVersionChecker(baseRequest: BaseRequest<String>)
        fun postLogin(login: Login)
    }
}