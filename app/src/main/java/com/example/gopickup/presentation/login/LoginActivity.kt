package com.example.gopickup.presentation.login

import android.os.Bundle
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityLoginBinding
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.hideKeyboard
import com.example.gopickup.utils.showToast

class LoginActivity : BaseActivity(), LoginContract.View {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this)
        presenter.start()
    }

    override fun initView() {
        super.initView()
        binding.layoutParent.setOnClickListener { hideKeyboard() }

        binding.btnLogin.setOnClickListener {
            val username = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            when {
                username.isEmpty() && password.isEmpty() -> {
                    binding.edtEmail.error = "Please input your email"
                    binding.edtEmail.requestFocus()
                    binding.edtPassword.error = "Please input your password"
                }
                username.isEmpty() -> binding.edtEmail.error = "Please input your email"
                password.isEmpty() -> binding.edtPassword.error = "Please input your password"
                else -> presenter.postLogin(username = username, password = password)
            }
        }
    }

    override fun showLoginSuccess(message: String) {
        showToast(message)

        NavigationUtils.navigateToOTPActivity(this)
        finish()
    }

    override fun showLoginFailed(message: String) {
        showToast(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}