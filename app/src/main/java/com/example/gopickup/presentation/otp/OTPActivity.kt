package com.example.gopickup.presentation.otp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityOTPBinding
import com.example.gopickup.model.request.Data
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.request.ResendOTPRequest
import com.example.gopickup.model.response.User
import com.example.gopickup.utils.*

class OTPActivity : BaseActivity(), OTPContract.View {

    private var _binding: ActivityOTPBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: OTPPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOTPBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OTPPresenter(this, callApi())
        presenter.start()
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.layoutParent.setOnClickListener { hideKeyboard() }
        setupEditText()

        val phoneNumber = intent.getStringExtra(Constant.KEY_PHONE_NUMBER)
        binding.tvMessage.text = "Please check your mobile number $phoneNumber\ncontinue to reset your password"

        // button next
        binding.btnNext.setOnClickListener {
            val edt1 = binding.edt1.text.toString()
            val edt2 = binding.edt2.text.toString()
            val edt3 = binding.edt3.text.toString()
            val edt4 = binding.edt4.text.toString()
            when {
                edt1.isNotEmpty() && edt2.isNotEmpty() && edt3.isNotEmpty() && edt4.isNotEmpty() -> {
                    val data = Data(
                        devid = provideDeviceId(),
                        email = preference.getString(Constant.KEY_EMAIL),
                        password = preference.getString(Constant.KEY_PASSWORD),
                        otp = "$edt1$edt2$edt3$edt4"
                    )
                    val login = Login(
                        data = data
                    )
                    presenter.postOTP(login = login)
                }
                else -> {
                    showToast("Please fill the OTP")
                }
            }

        }

        // button click here - Resend OTP
        binding.tvClickHere.setOnClickListener {
            val resendOTPRequest = ResendOTPRequest(
                email = preference.getString(Constant.KEY_EMAIL),
                hash = StringUtils.toMd5(input = "${Constant.RESEND_OTP}-${preference.getString(Constant.KEY_EMAIL)}")
            )
            presenter.postResendOTP(resendOTPRequest = BaseRequest(data = resendOTPRequest))
        }
    }

    override fun showOTPSuccess(user: User) {
        preference.saveBoolean(Constant.KEY_IS_LOGGED_IN, true)
        preference.saveString(Constant.KEY_USER_TYPE, UserType.PARTNER)
        preference.saveString(Constant.KEY_TOKEN, user.token!!)

        NavigationUtils.navigateToMainActivity(this)
        finish()
    }

    override fun showOTPFailed(message: String) {
        showToast(message)
    }

    override fun showResendOTPSuccess(message: String) {
        showToast(message)
    }

    override fun showResendOTPFailed(message: String) {
        showToast(message)
    }

    private fun setupEditText() {
        //GenericTextWatcher here works only for moving to next EditText when a number is entered
        //first parameter is the current EditText and second parameter is next EditText
        binding.edt1.addTextChangedListener(GenericTextWatcher(binding.edt1, binding.edt2))
        binding.edt2.addTextChangedListener(GenericTextWatcher(binding.edt2, binding.edt3))
        binding.edt3.addTextChangedListener(GenericTextWatcher(binding.edt3, binding.edt4))
        binding.edt4.addTextChangedListener(GenericTextWatcher(binding.edt4, null))

        //GenericKeyEvent here works for deleting the element and to switch back to previous EditText
        //first parameter is the current EditText and second parameter is previous EditText
        binding.edt1.setOnKeyListener(GenericKeyEvent(binding.edt1, null))
        binding.edt2.setOnKeyListener(GenericKeyEvent(binding.edt2, binding.edt1))
        binding.edt3.setOnKeyListener(GenericKeyEvent(binding.edt3, binding.edt2))
        binding.edt4.setOnKeyListener(GenericKeyEvent(binding.edt4, binding.edt3))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}

private class GenericKeyEvent internal constructor(
    private val currentView: EditText,
    private val previousView: EditText?
) : View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.edt_1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }


}

private class GenericTextWatcher internal constructor(
    private val currentView: View,
    private val nextView: View?
) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (currentView.id) {
            R.id.edt_1 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.edt_2 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.edt_3 -> if (text.length == 1) nextView!!.requestFocus()
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }
}
