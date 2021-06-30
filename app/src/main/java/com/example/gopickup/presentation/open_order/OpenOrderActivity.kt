package com.example.gopickup.presentation.open_order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityOpenOrderBinding
import com.example.gopickup.model.dummy.MyOrder
import com.example.gopickup.utils.DummyData

class OpenOrderActivity : BaseActivity(), OpenOrderContract.View {

    private var _binding: ActivityOpenOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: OpenOrderPresenter

    private val openOrderAdapter = OpenOrderAdapter {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOpenOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OpenOrderPresenter(this)
        presenter.start()
        presenter.getOpenOrders(DummyData.generateMyOrders())
    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Open Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun showOpenOrders(openOrderList: List<MyOrder>?) {
        openOrderList?.let {
            openOrderAdapter.addItems(it)

            binding.rvOpenOrder.apply {
                layoutManager = LinearLayoutManager(
                    this@OpenOrderActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = openOrderAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}