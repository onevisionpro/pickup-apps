package com.example.gopickup.utils

import android.app.Activity
import android.content.Intent
import com.example.gopickup.presentation.about_apps.AboutAppsActivity
import com.example.gopickup.presentation.change_order.ChangeOrderActivity
import com.example.gopickup.presentation.create_order.CreateOrderActivity
import com.example.gopickup.presentation.history.BA.BAActivity
import com.example.gopickup.presentation.history.details.HistoryDetailsActivity
import com.example.gopickup.presentation.open_order.details.book_order.OpenOrderDetailsForBookOrderActivity
import com.example.gopickup.presentation.login.LoginActivity
import com.example.gopickup.presentation.main.MainActivity
import com.example.gopickup.presentation.my_orders.MyOrdersActivity
import com.example.gopickup.presentation.my_orders.details.partner.take_item.MyOrderDetailsTakeOrderActivity
import com.example.gopickup.presentation.my_orders.details.partner.ba_details.SubmitBAActivity
import com.example.gopickup.presentation.my_orders.details.partner.received_item.MyOrderDetailsReceivedOrderActivity
import com.example.gopickup.presentation.my_orders.details.warehouse.MyOrderDetailsWarehouseActivity
import com.example.gopickup.presentation.open_order.OpenOrderActivity
import com.example.gopickup.presentation.order.OrderActivity
import com.example.gopickup.presentation.otp.OTPActivity
import com.example.gopickup.presentation.reset_password.ResetPasswordActivity
import com.example.gopickup.presentation.track_my_order.TrackMyOrderActivity

object NavigationUtils {

    fun navigateToLoginActivity(activity: Activity) {
        activity.startActivity(Intent(activity, LoginActivity::class.java))
    }

    fun navigateToOTPActivity(activity: Activity, phoneNumber: String) {
        val intent = Intent(activity, OTPActivity::class.java)
        intent.putExtra(Constants.KEY_PHONE_NUMBER, phoneNumber)
        activity.startActivity(intent)
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

    fun navigateToChangeOrderActivity(activity: Activity, trackId: String) {
        val intent = Intent(activity, ChangeOrderActivity::class.java)
        intent.putExtra(ChangeOrderActivity.TRACK_ID, trackId)
        activity.startActivity(intent)
    }

    fun navigateToMyOrderDetailsTakeOrderActivity(activity: Activity, trackId: String) {
        val intent = Intent(activity, MyOrderDetailsTakeOrderActivity::class.java)
        intent.putExtra(MyOrderDetailsTakeOrderActivity.TRACK_ID, trackId)
        activity.startActivity(intent)
    }

    fun navigateToMyOrderDetailsReceivedOrderActivity(activity: Activity, trackId: String) {
        val intent = Intent(activity, MyOrderDetailsReceivedOrderActivity::class.java)
        intent.putExtra(MyOrderDetailsReceivedOrderActivity.TRACK_ID, trackId)
        activity.startActivity(intent)
    }

    fun navigateToMyOrderDetailsWarehouseActivity(activity: Activity, trackId: String) {
        val intent = Intent(activity, MyOrderDetailsWarehouseActivity::class.java)
        intent.putExtra(MyOrderDetailsWarehouseActivity.TRACK_ID, trackId)
        activity.startActivity(intent)
    }

    fun navigateToTrackMyOrderActivity(activity: Activity) {
        activity.startActivity(Intent(activity, TrackMyOrderActivity::class.java))
    }

    fun navigateToAboutAppsActivity(activity: Activity) {
        activity.startActivity(Intent(activity, AboutAppsActivity::class.java))
    }

    fun navigateToOpenOrderActivity(activity: Activity) {
        activity.startActivity(Intent(activity, OpenOrderActivity::class.java))
    }

    fun navigateToOpenOrderDetailsForBookOrderActivity(activity: Activity, trackId: String) {
        val intent = Intent(activity, OpenOrderDetailsForBookOrderActivity::class.java)
        intent.putExtra(ChangeOrderActivity.TRACK_ID, trackId)
        activity.startActivity(intent)
    }

    fun navigateToHistoryDetailsActivity(activity: Activity, trackId: String) {
        val intent = Intent(activity, HistoryDetailsActivity::class.java)
        intent.putExtra(HistoryDetailsActivity.TRACK_ID, trackId)
        activity.startActivity(intent)
    }

    fun navigateToResetPasswordActivity(activity: Activity) {
        activity.startActivity(Intent(activity, ResetPasswordActivity::class.java))
    }

    fun navigateToSubmitBAOrderActivity(
        activity: Activity,
        trackId: String,
        warehouseName: String,
        status: String
    ) {
        val intent = Intent(activity, SubmitBAActivity::class.java)
        intent.putExtra(SubmitBAActivity.TRACK_ID, trackId)
        intent.putExtra(SubmitBAActivity.WH_NAME, warehouseName)
        intent.putExtra(SubmitBAActivity.STATUS, status)
        activity.startActivity(intent)
    }

    fun navigateToBAActivity(activity: Activity, trackId: String) {
        val intent = Intent(activity, BAActivity::class.java)
        intent.putExtra(BAActivity.TRACK_ID, trackId)
        activity.startActivity(intent)    }
}