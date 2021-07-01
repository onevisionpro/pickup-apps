package com.example.gopickup.presentation.otp

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.request.ResendOTPRequest
import com.example.gopickup.model.response.User

interface OTPContract {

    interface View : BaseView {
        fun showOTPSuccess(user: User)
        fun showOTPFailed(message: String)
        fun showResendOTPSuccess(message: String)
        fun showResendOTPFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun postOTP(login: Login)
        fun postResendOTP(resendOTPRequest: BaseRequest<ResendOTPRequest>)
    }
}