package com.example.gopickup.presentation.reset_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityResetPasswordBinding
import com.example.gopickup.model.request.ResetPassword
import com.example.gopickup.utils.hideKeyboard
import com.example.gopickup.utils.showToast

class ResetPasswordActivity : BaseActivity(), ResetPasswordContract.View {

    private var _binding: ActivityResetPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ResetPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ResetPasswordPresenter(this, callApi())
        presenter.start()
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.icBack.setOnClickListener { finish() }
        binding.toolbar.tvToolbarTitle.text = "Reset Password"
        binding.layoutParent.setOnClickListener { hideKeyboard() }

        binding.btnResetPassword.setOnClickListener {
            val newPassword = binding.edtNewPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()

            when {
                newPassword.isEmpty() -> {
                    binding.edtNewPassword.error = "Harap isi password"
                    binding.edtNewPassword.requestFocus()
                }
                confirmPassword.isEmpty() -> {
                    binding.edtConfirmPassword.error = "Harap isi konfirmasi password"
                    binding.edtConfirmPassword.requestFocus()
                }
                else -> {
                    val resetPassword = ResetPassword(
                        newPassword = newPassword,
                        newPasswordConf = confirmPassword
                    )
                    presenter.postResetPassword(BaseRequest(
                        guid = provideGUID(),
                        code = "0",
                        data = resetPassword
                    ))
                }
            }
        }
    }

    override fun showResetPasswordSuccess(message: String) {
        showToast(message)
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}