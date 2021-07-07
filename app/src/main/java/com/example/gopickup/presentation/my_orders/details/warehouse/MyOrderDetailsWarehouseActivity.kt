package com.example.gopickup.presentation.my_orders.details.warehouse

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityMyOrderDetailsWarehouseBinding
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.ItemOrder
import com.example.gopickup.model.response.OrderDetails
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogOrderArrivedListener

class MyOrderDetailsWarehouseActivity : BaseActivity(), MyOrderDetailsWarehouseContract.View {

    companion object {
        const val TRACK_ID = "TRACK_ID"
    }

    private var _binding: ActivityMyOrderDetailsWarehouseBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MyOrderDetailsWarehousePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrderDetailsWarehouseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MyOrderDetailsWarehousePresenter(this, callApi())
        presenter.start()
        presenter.getMyOrderDetails(trackId = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = TrackId(trackId = intent.getStringExtra(TRACK_ID))
        ))
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun showMyOrderDetails(orderDetails: OrderDetails) {
        binding.tvWarehouseName.text = orderDetails.orderTo
        binding.tvOrderId.text = "Order ID#${orderDetails.trackId}"
        binding.tvWarehouseNameCard.text = orderDetails.orderTo
        setupItemsLayout(orderDetails.items)
        binding.tvEstimateArrived.text = orderDetails.arrivalEstimate
        binding.tvOrderIdCard.text = orderDetails.trackId

        binding.btnOrderArrived.setOnClickListener {
            presenter.postOrderArrived(trackId = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = TrackId(trackId = orderDetails.trackId)
            ))
        }
    }

    override fun showOrderArrivedSuccess(message: String) {
        val trackId = intent.getStringExtra(TRACK_ID)!!
        DialogUtils.showDialogOrderArrived(this, trackId, object : IOnDialogOrderArrivedListener {
            override fun onBackToHomeClicked() {
                NavigationUtils.navigateToMainActivity(this@MyOrderDetailsWarehouseActivity)
                finish()
            }

        })
    }

    private fun setupItemsLayout(items: List<ItemOrder>?) {
        val itemOrderAdapter = ItemOrderAdapter()

        items?.let {
            itemOrderAdapter.addItems(it)
            binding.rvItems.apply {
                layoutManager = LinearLayoutManager(
                    this@MyOrderDetailsWarehouseActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = itemOrderAdapter
            }
            binding.rvItems.isNestedScrollingEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}