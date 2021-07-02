package com.example.gopickup.presentation.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivitySplashScreenBinding
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.NavigationUtils

class SplashScreenActivity : BaseActivity(), SplashScreenContract.View {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: SplashScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SplashScreenPresenter(this)
        presenter.start()

    }

    override fun initView() {
        super.initView()

        // navigate to login activity
        Handler(Looper.getMainLooper()).postDelayed({
            when (preference.getBoolean(Constants.KEY_IS_LOGGED_IN)) {
                true -> {
                    NavigationUtils.navigateToMainActivity(this)
                    finish()
                }
                false -> {
                    NavigationUtils.navigateToLoginActivity(this)
                    finish()
                }
            }
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}