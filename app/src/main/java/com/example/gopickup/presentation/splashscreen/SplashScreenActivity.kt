package com.example.gopickup.presentation.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivitySplashScreenBinding
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.SharedPreference
import com.example.gopickup.utils.firebase.MyFirebaseMessagingService
import com.example.gopickup.utils.firebase.NotificationListener
import com.google.firebase.messaging.FirebaseMessaging

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

        setNewFirebaseToken()
    }

    private fun setNewFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                val firebaseToken = it.result.toString()
                preference.saveString(Constants.KEY_FCM_TOKEN, firebaseToken)
                Log.d("TAG", "setNewFirebaseToken NewToken: $firebaseToken")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}