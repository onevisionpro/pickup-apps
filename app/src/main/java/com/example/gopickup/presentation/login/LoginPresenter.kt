package com.example.gopickup.presentation.login

class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun postLogin(username: String, password: String) {
        if (username == "mitra" && password == "mitra") {
            view.showLoginSuccessAsMitra("Success Login, Mitra")
        } else if (username == "warehouse" && password == "warehouse") {
            view.showLoginSuccessAsWarehouse("Success Login, Warehouse")
        } else {
            view.showLoginFailed("Username or Password is incorrect")
        }
    }

    override fun onDestroy() {

    }
}