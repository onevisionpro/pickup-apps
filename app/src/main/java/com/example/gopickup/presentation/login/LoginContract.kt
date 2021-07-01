package com.example.gopickup.presentation.login

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.Login

interface LoginContract {

    interface View : BaseView {
        fun showSendOTPSuccess(message: String)
        fun showSendOTPFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun postLogin(login: Login)
    }
}