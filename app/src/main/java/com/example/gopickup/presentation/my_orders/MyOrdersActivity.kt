package com.example.gopickup.presentation.my_orders

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityMyOrdersBinding
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.Order
import com.example.gopickup.utils.*

class MyOrdersActivity : BaseActivity(), MyOrdersContract.View {

    private var _binding: ActivityMyOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MyOrdersPresenter

    private val myOrdersAdapter = MyOrdersAdapter {
        when (preference.getString(Constants.KEY_USER_TYPE)) {
            UserType.WAREHOUSE -> {
                NavigationUtils.navigateToChangeOrderActivity(this, it.trackId!!)
            }
            UserType.PARTNER -> {
                NavigationUtils.navigateToMyOrderDetailsActivity(this, it.trackId!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MyOrdersPresenter(this, callApi())
        presenter.start()
        presenter.getMyOrderList(trackId = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = TrackId(trackId = "")
        ))
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "My Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun showMyOrderList(myOrderList: List<Order>?) {
        myOrderList?.let {
            if (it.isNotEmpty()) {
                myOrdersAdapter.addItems(it)

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