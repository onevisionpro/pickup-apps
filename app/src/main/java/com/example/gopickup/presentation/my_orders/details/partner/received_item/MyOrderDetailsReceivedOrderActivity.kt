package com.example.gopickup.presentation.my_orders.details.partner.received_item

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityMyOrderDetailsReceivedOrderBinding
import com.example.gopickup.model.request.ReceiveOrderRequest
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.ItemOrder
import com.example.gopickup.model.response.OrderDetails
import com.example.gopickup.presentation.history.details.ItemOrderAdapter
import com.example.gopickup.presentation.my_orders.details.partner.take_item.MyOrderDetailsTakeOrderActivity
import com.example.gopickup.utils.*

class MyOrderDetailsReceivedOrderActivity : BaseActivity(),
    MyOrderDetailsReceivedOrderContract.View {

    companion object {
        const val TRACK_ID = "TRACK_ID"
    }

    private var _binding: ActivityMyOrderDetailsReceivedOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MyOrderDetailsReceivedOrderPresenter
    private lateinit var warehouseName: String
    private var receiveOrderRequest = ReceiveOrderRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrderDetailsReceivedOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MyOrderDetailsReceivedOrderPresenter(this, callApi())
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
        binding.tvWarehouseFrom.text = orderDetails.orderFrom
        binding.tvCreatedBy.text = orderDetails.createBy
        binding.tvOrderIdCard.text = orderDetails.trackId
        binding.tvReceipt.text = orderDetails.resiCode
        binding.tvWarehouseStatus.text = orderDetails.progressOrder?.get(4)?.status ?: "-"
        binding.tvNotes.text = orderDetails.notes

        binding.btnTakePhoto.setOnClickListener {
            openCamera()
        }

        binding.btnReceived.setOnClickListener {
            receiveOrderRequest.trackId = orderDetails.trackId

            presenter.postReceiveOrder(receiveOrderRequest = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = receiveOrderRequest
            ))
        }
    }

    override fun showReceiveOrderSuccess(message: String) {
        showToast(message)
        NavigationUtils.navigateToSubmitBAOrderActivity(
            this,
            trackId =  intent.getStringExtra(MyOrderDetailsTakeOrderActivity.TRACK_ID)!!,
            warehouseName = warehouseName,
            status = OrderStatus.ARRIVED
        )
    }

    private fun setupItemsLayout(items: List<ItemOrder>?) {
        val itemOrderAdapter = ItemOrderAdapter()

        items?.let {
            itemOrderAdapter.addItems(it)
            binding.rvItems.apply {
                layoutManager = LinearLayoutManager(
                    this@MyOrderDetailsReceivedOrderActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = itemOrderAdapter
            }
            binding.rvItems.isNestedScrollingEnabled = false
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                receiveOrderRequest.imageProven = ImageUtils.toBase64(imageBitmap)

                binding.imgItem.setImageBitmap(imageBitmap)
            }
        }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}