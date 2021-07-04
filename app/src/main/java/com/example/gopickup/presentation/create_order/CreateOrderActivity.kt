package com.example.gopickup.presentation.create_order

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityCreateOrderBinding
import com.example.gopickup.model.response.ItemWarehouse
import com.example.gopickup.model.response.Warehouse
import com.example.gopickup.presentation.main.MainActivity
import com.example.gopickup.utils.DateUtils
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.StringUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogCreateOrderListener
import com.example.gopickup.utils.dialog.listener.IOnItemClicked
import com.example.gopickup.utils.showToast
import java.util.*

class CreateOrderActivity : BaseActivity(), CreateOrderContract.View {

    private var _binding: ActivityCreateOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: CreateOrderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CreateOrderPresenter(this, callApi())
        presenter.start()
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Create Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.edtChooseWarehouse.setOnClickListener {
            presenter.getWarehouseList(
                baseRequest = BaseRequest(
                    guid = provideGUID(),
                    code = "",
                    data = ""
                )
            )
        }

        binding.edtChooseItem.setOnClickListener {
            presenter.getItemList(
                baseRequest = BaseRequest(
                    guid = provideGUID(),
                    code = "",
                    data = ""
                )
            )
        }

        var qty = binding.tvQtyCounter.text.toString().toInt()
        binding.btnQtyPlus.setOnClickListener {
            if (qty > 0) {
                qty++
                binding.tvQtyCounter.text = qty.toString()
            }
        }

        binding.btnQtyMinus.setOnClickListener {
            if (qty > 1) {
                qty--
                binding.tvQtyCounter.text = qty.toString()
            }
        }

        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.edtEstimatedDate.setText(DateUtils.formatDate(cal.time))
            }
        binding.edtEstimatedDate.setOnClickListener {
//            DatePickerDialog(
//                this,
//                dateSetListener,
//                cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH)
//            ).show()
            DialogUtils.showDialogCalendar(this, object : IOnItemClicked<Date> {
                override fun onItemClicked(data: Date) {
                    binding.edtEstimatedDate.setText(DateUtils.formatDate(data))
                }

            })
        }

        binding.btnOrder.setOnClickListener {
            DialogUtils.showDialogCreateOrder(this, object : IOnDialogCreateOrderListener {
                override fun onHistoryOrderClicked() {
                    val intent = Intent(this@CreateOrderActivity, MainActivity::class.java)
                    intent.putExtra("NAVIGATE_TO", "HISTORY")
                    startActivity(intent)
                    finish()
                }

                override fun onBackToHomeClicked() {
                    NavigationUtils.navigateToMainActivity(this@CreateOrderActivity)
                    finish()
                }

            })
        }
    }

    override fun showWarehouseList(warehouseList: List<Warehouse>?) {
        warehouseList?.let {
            DialogUtils.showDialogWarehouse(this, it,
                object : IOnItemClicked<Warehouse> {
                    override fun onItemClicked(data: Warehouse) {
                        binding.edtChooseWarehouse.setText(data.whName)
                        showToast("id warehouse : ${data.idWarehouse}")
                    }

                })
        }
    }

    override fun showItemList(itemList: List<ItemWarehouse>?) {
        Log.d("TAG", "showItemList: $itemList")
        itemList?.let {
            DialogUtils.showDialogItems(this, it, object : IOnItemClicked<ItemWarehouse> {
                override fun onItemClicked(data: ItemWarehouse) {
                    binding.edtChooseItem.setText(data.itemName)
                    showToast("id item : ${data.idItem}")
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}