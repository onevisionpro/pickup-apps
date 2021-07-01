package com.example.gopickup.presentation.login

import android.os.Bundle
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityLoginBinding
import com.example.gopickup.model.request.Data
import com.example.gopickup.model.request.Login
import com.example.gopickup.utils.*

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
                        devid = "123123",
                        email = email,
                        password = password,
                        otp = ""
                    )
                    val login = Login(
                        data = data
                    )
                    presenter.postLogin(login = login)
                }
            }
        }
    }

    override fun showSendOTPSuccess(message: String) {
        showToast(message)
        NavigationUtils.navigateToOTPActivity(this)
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