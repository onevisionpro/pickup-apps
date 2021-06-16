package com.example.gopickup.presentation.login

class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {

    override fun start() {
        view.initView()
    }

    override fun postLogin(username: String, password: String) {
        if (username == "admin" && password == "admin") {
            view.showLoginSuccess("Welcome...")
        } else {
            view.showLoginFailed("Username or Password is incorrect")
        }
    }

    override fun onDestroy() {

    }
}