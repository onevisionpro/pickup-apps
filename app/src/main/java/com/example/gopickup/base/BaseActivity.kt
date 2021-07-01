package com.example.gopickup.base

import android.annotation.SuppressLint
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.network.ApiClient
import com.example.gopickup.network.ApiRest
import com.example.gopickup.utils.SharedPreference
import com.example.gopickup.utils.showToast
import java.security.Security

open class BaseActivity : AppCompatActivity(), BaseView {

    lateinit var progressBar: ProgressBar
    lateinit var preference: SharedPreference

    fun callApi(): AppRepositoryImpl {
        val service = ApiClient().getClient().create(ApiRest::class.java)
        return AppRepositoryImpl(service)
    }

    override fun initView() {
        preference = SharedPreference(this)
    }

    fun initProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
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

    @SuppressLint("HardwareIds")
    fun provideDeviceId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }

}