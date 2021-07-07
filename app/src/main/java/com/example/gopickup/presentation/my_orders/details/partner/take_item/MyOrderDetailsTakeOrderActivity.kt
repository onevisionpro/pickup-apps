package com.example.gopickup.presentation.my_orders.details.partner.take_item

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityMyOrderDetailsBinding
import com.example.gopickup.model.request.TakeOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.ItemOrder
import com.example.gopickup.model.response.Order
import com.example.gopickup.model.response.OrderDetails
import com.example.gopickup.presentation.history.details.ItemOrderAdapter
import com.example.gopickup.utils.*

class MyOrderDetailsTakeOrderActivity : BaseActivity(), MyOrderDetailsContract.View {

    companion object {
        const val TRACK_ID = "TRACK_ID"
        const val STATUS = "STATUS"
    }

    private var _binding: ActivityMyOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MyOrderDetailsPresenter
    private lateinit var warehouseName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MyOrderDetailsPresenter(this, callApi())
        presenter.start()
        presenter.getMyOrderDetails(
            trackId = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = TrackId(trackId = intent.getStringExtra(TRACK_ID))
            )
        )
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
        binding.layoutParent.setOnClickListener { hideKeyboard() }
    }

    override fun showMyOrderDetails(orderDetails: OrderDetails) {
        warehouseName = orderDetails.orderTo!!
        binding.tvWarehouseName.text = orderDetails.orderTo
        binding.tvOrderId.text = "Order ID #${orderDetails.trackId}"
        binding.tvWarehouseNameCard.text = orderDetails.orderTo
        setupItemsLayout(orderDetails.items)
        binding.tvEstimateArrived.text = orderDetails.arrivalEstimate
        binding.tvOrderIdCard.text = orderDetails.trackId
        binding.edtReceipt.setText(orderDetails.resiCode)
        binding.edtNote.setText(orderDetails.notes)

        binding.btnTakeItem.setOnClickListener {
            val receipt = binding.edtReceipt.text.toString()
            val notes = binding.edtNote.text.toString()

            when {
                receipt.isEmpty() -> {
                    binding.edtReceipt.error = "Isi resi terlebih dahulu"
                    binding.edtReceipt.requestFocus()
                }
                else -> {
                    val takeOrder = TakeOrder(
                        trackId = orderDetails.trackId,
                        resiCode = receipt,
                        notes = notes
                    )
                    presenter.postTakeOrder(
                        takeOrder = BaseRequest(
                            guid = provideGUID(),
                            code = "",
                            data = takeOrder
                        )
                    )
                }
            }
        }
    }

    override fun showTakeOrderSuccess(message: String) {
        showToast(message)
        NavigationUtils.navigateToSubmitBAOrderActivity(
            this,
            trackId =  intent.getStringExtra(TRACK_ID)!!,
            warehouseName = warehouseName,
            status = OrderStatus.TAKE_ITEM
        )
    }

    private fun setupItemsLayout(items: List<ItemOrder>?) {
        val itemOrderAdapter = ItemOrderAdapter()

        items?.let {
            itemOrderAdapter.addItems(it)
            binding.rvItems.apply {
                layoutManager = LinearLayoutManager(
                    this@MyOrderDetailsTakeOrderActivity,
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