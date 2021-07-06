package com.example.gopickup.presentation.about_apps

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.request.Type
import com.example.gopickup.model.response.AboutApps

interface AboutAppsContract {

    interface View : BaseView {
        fun showWordings(aboutApps: AboutApps)
    }

    interface Presenter : BasePresenter {
        fun getWordings(moreType: BaseRequest<Type>)
    }
}