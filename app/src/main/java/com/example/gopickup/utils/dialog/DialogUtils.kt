package com.example.gopickup.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.example.gopickup.databinding.DialogCreateOrderBinding
import java.util.*

object DialogUtils {

    fun showDialogCreateOrder(context: Context, listener: IOnDialogCreateOrderClicked) {
        val dialog = Dialog(context)
        val binding = DialogCreateOrderBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnHistoryOrder.setOnClickListener {
            listener.onHistoryOrderClicked()
        }

        binding.btnBackToHome.setOnClickListener {
            listener.onBackToHomeClicked()
        }

        dialog.show()
    }
}