package com.example.gopickup.base

interface BaseView {
    fun initView()
    fun showLoading()
    fun hideLoading()
    fun showMessage(message: String?)
    fun showSessionExpired(message: String?)
}