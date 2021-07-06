package com.example.gopickup.presentation.open_order.details.book_order

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityOpenOrderDetailsBinding
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.ItemOrder
import com.example.gopickup.model.response.OrderDetails
import com.example.gopickup.presentation.history.details.ItemOrderAdapter
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogOrderBookedListener

class OpenOrderDetailsForBookOrderActivity : BaseActivity(), OpenOrderDetailsContract.View {

    companion object {
        const val TRACK_ID = "TRACK_ID"
    }

    private var _binding: ActivityOpenOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: OpenOrderDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOpenOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OpenOrderDetailsPresenter(this, callApi())
        presenter.start()
        presenter.getOpenOrderDetails(trackId = BaseRequest(
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

        binding.btnBookOrder.setOnClickListener {
//            DialogUtils.showDialogOrderBooked(this, object : IOnDialogOrderBookedListener {
//                override fun onBackToHomeClicked() {
//                    NavigationUtils.navigateToMainActivity(this@OpenOrderDetailsForBookOrderActivity)
//                    finish()
//                }
//
//            })
        }
    }

    override fun showOpenOrderDetails(orderDetails: OrderDetails) {
        binding.tvWarehouseName.text = orderDetails.orderTo
        binding.tvOrderId.text = "Order ID #${orderDetails.trackId}"
        binding.tvWarehouseNameCard.text = orderDetails.orderTo
        setupItemsLayout(orderDetails.items)
        binding.tvEstimateArrived.text = orderDetails.arrivalEstimate
        binding.tvOrderIdCard.text = orderDetails.trackId

        binding.btnBookOrder.setOnClickListener {

        }
    }

    private fun setupItemsLayout(items: List<ItemOrder>?) {
        val itemOrderAdapter = ItemOrderAdapter()

        items?.let {
            itemOrderAdapter.addItems(it)
            binding.rvItems.apply {
                layoutManager = LinearLayoutManager(
                    this@OpenOrderDetailsForBookOrderActivity,
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