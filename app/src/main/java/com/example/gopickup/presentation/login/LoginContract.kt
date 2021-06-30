package com.example.gopickup.presentation.login

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseView

interface LoginContract {

    interface View : BaseView {
        fun showLoginSuccessAsMitra(message: String)
        fun showLoginSuccessAsWarehouse(message: String)
        fun showLoginFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun postLogin(username: String, password: String)
    }
}