package com.example.gopickup.utils

import android.app.Activity
import android.content.Intent
import com.example.gopickup.presentation.create_order.CreateOrderActivity
import com.example.gopickup.presentation.login.LoginActivity
import com.example.gopickup.presentation.main.MainActivity
import com.example.gopickup.presentation.my_orders.MyOrdersActivity
import com.example.gopickup.presentation.order.OrderActivity
import com.example.gopickup.presentation.otp.OTPActivity

object NavigationUtils {

    fun navigateToLoginActivity(activity: Activity) {
        activity.startActivity(Intent(activity, LoginActivity::class.java))
    }

    fun navigateToOTPActivity(activity: Activity) {
        activity.startActivity(Intent(activity, OTPActivity::class.java))
    }

    fun navigateToMainActivity(activity: Activity) {
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }

    fun navigateToCreateOrderActivity(activity: Activity) {
        activity.startActivity(Intent(activity, CreateOrderActivity::class.java))
    }

    fun navigateToOrderActivity(activity: Activity) {
        activity.startActivity(Intent(activity, OrderActivity::class.java))
    }

    fun navigateToMyOrdersActivity(activity: Activity) {
        activity.startActivity(Intent(activity, MyOrdersActivity::class.java))
    }
}