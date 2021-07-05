package com.example.gopickup.presentation.reset_password

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.ResetPassword

interface ResetPasswordContract {

    interface View : BaseView {
        fun showResetPasswordSuccess(message: String)
    }

    interface Presenter : BasePresenter {
        fun postResetPassword(resetPassword: BaseRequest<ResetPassword>)
    }
}