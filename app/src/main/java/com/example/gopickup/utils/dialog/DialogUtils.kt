package com.example.gopickup.utils.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.databinding.*
import com.example.gopickup.model.response.ItemWarehouse
import com.example.gopickup.model.response.Warehouse
import com.example.gopickup.utils.dialog.adapter.ItemAdapter
import com.example.gopickup.utils.dialog.adapter.StatusAdapter
import com.example.gopickup.utils.dialog.adapter.WarehouseAdapter
import com.example.gopickup.utils.dialog.listener.*
import java.util.*

object DialogUtils {

    fun showDialogCreateOrder(context: Context, trackId: String, listener: IOnDialogCreateOrderListener) {
        val dialog = Dialog(context)
        val binding = DialogCreateOrderBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOrderId.text = trackId

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

    fun showDialogChangeMyOrder(context: Context, orderId: String, listener: IOnDialogChangeMyOrderListener) {
        val dialog = Dialog(context)
        val binding = DialogChangeOrderBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOrderId.text = orderId
        binding.btnBackToHome.setOnClickListener {
            dialog.hide()
            listener.onBackToHomeClicked()
        }

        dialog.show()
    }

    fun showDialogCancelOrder(context: Context, orderId: String, listener: IOnDialogCancelOrderListener) {
        val dialog = Dialog(context)
        val binding = DialogCancelOrderBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOrderId.text = orderId
        binding.btnBackToHome.setOnClickListener {
            dialog.hide()
            listener.onBackToHomeClicked()
        }

        dialog.show()
    }

    fun showDialogOrderBooked(context: Context, trackId: String, listener: IOnDialogOrderBookedListener) {
        val dialog = Dialog(context)
        val binding = DialogOrderBookBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOrderId.text = trackId
        binding.btnBackToOpenOrder.setOnClickListener {
            listener.onBackToOpenOrderClicked()
            dialog.hide()
        }
        binding.btnMyOrder.setOnClickListener {
            listener.onMyOrderClicked()
            dialog.hide()
        }

        dialog.show()
    }

    fun showDialogNewUpdateVersion(
        context: Context,
        versionName: String,
        listener: IOnDialogUpdateVersionListener
    ) {
        val dialog = Dialog(context)
        val binding = DialogNewUpdateBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvVersion.text = versionName
        binding.btnUpdate.setOnClickListener {
            dialog.hide()
            listener.onUpdateClicked()
        }

        dialog.show()
    }

    fun showDialogWarehouse(
        context: Context,
        warehouseList: List<Warehouse>,
        listener: IOnItemClicked<Warehouse>
    ) {
        val dialog = Dialog(context)
        val binding = DialogWarehouseBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val warehouseAdapter = WarehouseAdapter {
            listener.onItemClicked(it)
            dialog.hide()
        }
        warehouseAdapter.addItems(warehouseList)
        binding.rvWarehouses.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = warehouseAdapter
        }

        // filter
        binding.edtFindWarehouse.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                warehouseAdapter.filter.filter(s)
                binding.rvWarehouses.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter = warehouseAdapter
                }
            }

        })

        dialog.show()
    }

    fun showDialogItems(
        context: Context,
        itemList: List<ItemWarehouse>,
        listener: IOnItemClicked<ItemWarehouse>
    ) {
        val dialog = Dialog(context)
        val binding = DialogItemBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val itemAdapter = ItemAdapter {
            listener.onItemClicked(it)
            dialog.hide()
        }
        itemAdapter.addItems(itemList)
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = itemAdapter
        }

        dialog.show()
    }

    fun showDialogCalendar(context: Context, listener: IOnItemClicked<Date>) {
        val cal = Calendar.getInstance()
        val YEAR = cal.get(Calendar.YEAR)
        val MONTH = cal.get(Calendar.MONTH)
        val DAY = cal.get(Calendar.DAY_OF_MONTH)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                listener.onItemClicked(cal.time)
            }

        val datePickerDialog = DatePickerDialog(
            context,
            dateSetListener,
            YEAR,
            MONTH,
            DAY
        )
        datePickerDialog.show()
    }

    fun showDialogSendOrder(context: Context, trackId: String, listener: IOnDialogSendOrderListener) {
        val dialog = Dialog(context)
        val binding = DialogSendOrderBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOrderId.text = trackId
        binding.btnBackToHome.setOnClickListener {
            listener.onBackToHomeClicked()
            dialog.hide()
        }

        dialog.show()
    }

    fun showDialogOrderArrived(context: Context, trackId: String, listener: IOnDialogOrderArrivedListener) {
        val dialog = Dialog(context)
        val binding = DialogOrderArrivedBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOrderId.text = trackId
        binding.btnBackToHome.setOnClickListener {
            listener.onBackToHomeClicked()
            dialog.hide()
        }

        dialog.show()
    }

    fun showDialogReceivedOrder(context: Context, trackId: String, listener: IOnDialogReceivedOrderListener) {
        val dialog = Dialog(context)
        val binding = DialogOrderReceivedBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOrderId.text = trackId
        binding.btnBackToHome.setOnClickListener {
            listener.onBackToHomeClicked()
            dialog.hide()
        }

        dialog.show()
    }

    fun showDialogStatus(
        context: Context,
        itemList: List<String>,
        listener: IOnItemClicked<String>
    ) {
        val dialog = Dialog(context)
        val binding = DialogItemBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val statusAdapter = StatusAdapter {
            listener.onItemClicked(it)
            dialog.hide()
        }
        statusAdapter.addItems(itemList)
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = statusAdapter
        }

        dialog.show()
    }

    fun showDialogBAPreview(context: Context, content: String) {
        val dialog = Dialog(context, R.style.Theme_Dialog_Preview)
        val binding = DialogBaPreviewBinding.inflate(LayoutInflater.from(context))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.webview.loadDataWithBaseURL(
            null,
            content,
            "text/html",
            "UTF-8",
            null
        )

        binding.layoutParent.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}