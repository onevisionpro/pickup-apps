package com.example.gopickup.presentation.profile

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.response.Profile

interface ProfileContract {

    interface View : BaseView {
        fun showProfile(profile: Profile?)
    }

    interface Presenter : BasePresenter {
        fun getProfile(profileRequest: BaseRequest<String>)
    }
}