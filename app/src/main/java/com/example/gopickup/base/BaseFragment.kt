package com.example.gopickup.base

import android.annotation.SuppressLint
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.network.ApiClient
import com.example.gopickup.network.ApiRest
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.SharedPreference
import com.example.gopickup.utils.StringUtils
import com.example.gopickup.utils.showToast

open class BaseFragment: Fragment(), BaseView {

    lateinit var progressBar: ProgressBar
    lateinit var preference: SharedPreference

    fun callApi(): AppRepositoryImpl {
        val service = ApiClient().getClient().create(ApiRest::class.java)
        return AppRepositoryImpl(service)
    }

    fun initProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    override fun initView() {
        preference = SharedPreference(requireContext())
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showMessage(message: String?) {
        showToast(message!!)
    }

    override fun showSessionExpired(message: String?) {
        showToast(message!!)
    }

    @SuppressLint("HardwareIds")
    fun provideDeviceId(): String {
        return Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun provideGUID(): String {
        val token = preference.getString(Constants.KEY_TOKEN)
        val devId = provideDeviceId()
        return StringUtils.toMd5("$token$devId")
    }

}