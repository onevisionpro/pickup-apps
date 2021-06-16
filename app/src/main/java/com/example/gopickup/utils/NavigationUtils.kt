package com.example.gopickup.utils

import android.app.Activity
import android.content.Intent
import com.example.gopickup.presentation.login.LoginActivity
import com.example.gopickup.presentation.main.MainActivity

object NavigationUtils {

    fun navigateToLoginActivity(activity: Activity) {
        activity.startActivity(Intent(activity, LoginActivity::class.java))
    }

    fun navigateToMainActivity(activity: Activity) {
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}