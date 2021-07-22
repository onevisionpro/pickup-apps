package com.example.gopickup.presentation.open_order

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityOpenOrderBinding
import com.example.gopickup.model.dummy.MyOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.Order
import com.example.gopickup.utils.DummyData
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.hide
import com.example.gopickup.utils.show

class OpenOrderActivity : BaseActivity(), OpenOrderContract.View {

    private var _binding: ActivityOpenOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: OpenOrderPresenter

    private val openOrderAdapter = OpenOrderAdapter {
        NavigationUtils.navigateToOpenOrderDetailsForBookOrderActivity(this, it.trackId!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOpenOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OpenOrderPresenter(this, callApi())
        presenter.start()
        presenter.getOpenOrderList(trackId = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = TrackId(trackId = "")
        ))
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Pesanan Tersedia"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun showOpenOrderList(openOrderList: List<Order>?) {
        openOrderList?.let {
            if (it.isNotEmpty()) {
                openOrderAdapter.addItems(it)

                binding.rvOpenOrder.apply {
                    layoutManager = LinearLayoutManager(
                        this@OpenOrderActivity,
                        RecyclerView.VERTICAL,
                        false
                    )
                    adapter = openOrderAdapter
                }
            } else {
                binding.tvNoOrderItems.show()
                binding.rvOpenOrder.hide()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getOpenOrderList(trackId = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = TrackId(trackId = "")
        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}