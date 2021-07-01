package com.example.gopickup.presentation.login

import android.os.Bundle
import android.util.Log
import com.example.gopickup.BuildConfig
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.databinding.ActivityLoginBinding
import com.example.gopickup.model.request.Data
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.response.User
import com.example.gopickup.model.response.VersionChecker
import com.example.gopickup.utils.*
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogUpdateVersionListener

class LoginActivity : BaseActivity(), LoginContract.View {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this, callApi())
        presenter.start()
        presenter.postVersionChecker(
            baseRequest = BaseRequest(
                guid = "OVP2021#PickUpMobile",
                code = "0",
                data = ""
            )
        )
    }

    override fun initView() {
        super.initView()
        binding.layoutParent.setOnClickListener { hideKeyboard() }
        initProgressBar(binding.progressBar)

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            when {
                email.isEmpty() && password.isEmpty() -> {
                    binding.edtEmail.error = "Please input your email"
                    binding.edtEmail.requestFocus()
                    binding.edtPassword.error = "Please input your password"
                }
                email.isEmpty() -> binding.edtEmail.error = "Please input your email"
                password.isEmpty() -> binding.edtPassword.error = "Please input your password"
                else -> {
                    val data = Data(
                        devid = provideDeviceId(),
                        email = email,
                        password = password,
                        otp = ""
                    )
                    val login = Login(
                        data = data
                    )
                    presenter.postLogin(login = login)

                    preference.saveString(Constant.KEY_EMAIL, email)
                    preference.saveString(Constant.KEY_PASSWORD, password)
                }
            }
        }
    }

    override fun showVersionChecker(versionChecker: VersionChecker) {
        val versionName = BuildConfig.VERSION_NAME
        if (versionName != versionChecker.updatedVersion) {
            if (versionChecker.pushUpdate == PushUpdateStatus.YES) {
                DialogUtils.showDialogNewUpdateVersion(this, versionChecker.updatedVersion!!,
                    object : IOnDialogUpdateVersionListener {
                        override fun onUpdateClicked() {
                            showToast("clicked")
                        }

                    })
            }
        }
    }

    override fun showLoginSuccessForWarehouse(baseResponse: BaseResponse<User>) {
        showToast(baseResponse.info!!)

        preference.saveBoolean(Constant.KEY_IS_LOGGED_IN, true)
        preference.saveString(Constant.KEY_USER_TYPE, UserType.WAREHOUSE)
        preference.saveString(Constant.KEY_TOKEN, baseResponse.data?.token!!)

        NavigationUtils.navigateToMainActivity(this)
        finish()
    }

    override fun showSendOTPSuccess(message: String) {
        showToast(message)

        val phoneNumber = message.substring(message.indexOf("["))
        NavigationUtils.navigateToOTPActivity(this, phoneNumber)
        finish()
    }

    override fun showSendOTPFailed(message: String) {
        showToast(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}