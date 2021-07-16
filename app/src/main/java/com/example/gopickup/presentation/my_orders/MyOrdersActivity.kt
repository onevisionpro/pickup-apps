package com.example.gopickup.presentation.my_orders

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityMyOrdersBinding
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.Order
import com.example.gopickup.presentation.history.filter.HistoryFilterFragment
import com.example.gopickup.presentation.my_orders.filter.MyOrderFilterFragment
import com.example.gopickup.utils.*

class MyOrdersActivity : BaseActivity(), MyOrdersContract.View {

    private var _binding: ActivityMyOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MyOrdersPresenter

    private val myOrdersAdapter = MyOrdersAdapter {
        when (preference.getString(Constants.KEY_USER_TYPE)) {
            UserType.WAREHOUSE -> {
                when (it.status) {
                    OrderStatus.ORDER_CREATED -> {
                        NavigationUtils.navigateToChangeOrderActivity(this, it.trackId!!)
                    }
                    OrderStatus.BOOKED -> {
                        NavigationUtils.navigateToChangeOrderActivity(this, it.trackId!!)
                    }
                    OrderStatus.TAKE_ITEM -> {
                        NavigationUtils.navigateToChangeOrderActivity(this, it.trackId!!)
                    }
                    else -> {
                        NavigationUtils.navigateToMyOrderDetailsWarehouseActivity(this, it.trackId!!)
                    }
                }
            }
            UserType.PARTNER -> {
                when (it.status) {
                    OrderStatus.BOOKED -> {
                        NavigationUtils.navigateToMyOrderDetailsTakeOrderActivity(this, it.trackId!!)
                    }
                    OrderStatus.ACCEPT_WH -> {
                        NavigationUtils.navigateToMyOrderDetailsReceivedOrderActivity(this, it.trackId!!)
                    }
                    else -> {
                        NavigationUtils.navigateToMyOrderDetailsWarehouseActivity(this, it.trackId!!)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MyOrdersPresenter(this, callApi())
        presenter.start()
        presenter.getMyOrderList(
            trackId = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = TrackId(trackId = "")
            )
        )
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "My Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.filter.setOnClickListener {
            val bottomSheetFilter = MyOrderFilterFragment()
            bottomSheetFilter.show(supportFragmentManager, bottomSheetFilter.tag)
        }
    }

    override fun showMyOrderList(myOrderList: List<Order>?) {
        myOrderList?.let { orderList ->
            val trackId = intent.getStringExtra("track_id")
            val status = intent.getStringExtra("status")

            if (orderList.isNotEmpty()) {
                when {
                    !trackId.isNullOrEmpty() && !status.isNullOrEmpty() -> {
                        val myOrderListFilter = myOrderList.filter { it.trackId == trackId && it.status == status }
                        myOrdersAdapter.addItems(myOrderListFilter)
                        if (myOrderListFilter.isEmpty()) binding.tvNoOrderItems.show()

                        binding.layoutStatus.show()
                        binding.tvStatus.text = status
                        binding.layoutTrackId.show()
                        binding.tvTrackId.text = trackId
                    }
                    !trackId.isNullOrEmpty() -> {
                        val myOrderListFilter = myOrderList.filter { it.trackId == trackId }
                        myOrdersAdapter.addItems(myOrderListFilter)
                        if (myOrderListFilter.isEmpty()) binding.tvNoOrderItems.show()

                        binding.layoutTrackId.show()
                        binding.tvTrackId.text = trackId
                    }
                    !status.isNullOrEmpty() -> {
                        val myOrderListFilter = myOrderList.filter { it.status == status }
                        myOrdersAdapter.addItems(myOrderListFilter)
                        if (myOrderListFilter.isEmpty()) binding.tvNoOrderItems.show()

                        binding.layoutStatus.show()
                        binding.tvStatus.text = status
                    }
                    else -> {
                        myOrdersAdapter.addItems(orderList)
                    }
                }
                binding.rvMyOrders.apply {
                    layoutManager = LinearLayoutManager(
                        this@MyOrdersActivity,
                        RecyclerView.VERTICAL,
                        false
                    )
                    adapter = myOrdersAdapter
                }
            } else {
                binding.tvNoOrderItems.show()
                binding.rvMyOrders.hide()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}