package com.example.gopickup.presentation.profile

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.dummy.Profile

interface ProfileContract {

    interface View : BaseView {
        fun showProfile(profile: Profile)
    }

    interface Presenter : BasePresenter {
        fun getProfile(profile: Profile)
    }
}