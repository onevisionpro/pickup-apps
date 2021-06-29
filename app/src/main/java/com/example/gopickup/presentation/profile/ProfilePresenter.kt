package com.example.gopickup.presentation.profile

import com.example.gopickup.model.dummy.Profile

class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter {
    override fun getProfile(profile: Profile) {
        view.showProfile(profile)
    }

    override fun start() {
        view.initView()
    }

    override fun onDestroy() {

    }
}