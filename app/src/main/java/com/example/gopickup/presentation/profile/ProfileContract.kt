package com.example.gopickup.presentation.profile

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.EditProfile
import com.example.gopickup.model.response.Profile

interface ProfileContract {

    interface View : BaseView {
        fun showProfile(profile: Profile?)
        fun showEditProfileSuccess(message: String)
    }

    interface Presenter : BasePresenter {
        fun getProfile(profileRequest: BaseRequest<String>)
        fun postEditProfile(profile: BaseRequest<EditProfile>)
    }
}