package com.example.gopickup.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.example.gopickup.databinding.DialogCancelOrderBinding
import com.example.gopickup.databinding.DialogChangeOrderBinding
import com.example.gopickup.databinding.DialogCreateOrderBinding
import com.example.gopickup.databinding.DialogOrderBookedBinding
import com.example.gopickup.utils.dialog.listener.IOnDialogCancelOrderListener
import com.example.gopickup.utils.dialog.listener.IOnDialogChangeMyOrderListener
import com.example.gopickup.utils.dialog.listener.IOnDialogCreateOrderListener
import com.example.gopickup.utils.dialog.listener.IOnDialogOrderBookedListener
import java.util.*

object DialogUtils {

    fun showDialogCreateOrder(context: Context, listener: IOnDialogCreateOrderListener) {
        val dialog = Dialog(context)
        val binding = DialogCreateOrderBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnHistoryOrder.setOnClickListener {
            dialog.hide()
            listener.onHistoryOrderClicked()
        }

        binding.btnBackToHome.setOnClickListener {
            dialog.hide()
            listener.onBackToHomeClicked()
        }

        dialog.show()
    }

    fun showDialogChangeMyOrder(context: Context, listener: IOnDialogChangeMyOrderListener) {
        val dialog = Dialog(context)
        val binding = DialogChangeOrderBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnTrackMyOrder.setOnClickListener {
            dialog.hide()
            listener.onTrackMyOrderClicked()
        }

        binding.btnBackToHome.setOnClickListener {
            dialog.hide()
            listener.onBackToHomeClicked()
        }

        dialog.show()
    }

    fun showDialogCancelOrder(context: Context, listener: IOnDialogCancelOrderListener) {
        val dialog = Dialog(context)
        val binding = DialogCancelOrderBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnBackToHome.setOnClickListener {
            dialog.hide()
            listener.onBackToHomeClicked()
        }

        dialog.show()
    }

    fun showDialogOrderBooked(context: Context, listener: IOnDialogOrderBookedListener) {
        val dialog = Dialog(context)
        val binding = DialogOrderBookedBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnBackToHome.setOnClickListener {
            listener.onBackToHomeClicked()
            dialog.hide()
        }

        dialog.show()
    }
}