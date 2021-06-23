package com.example.gopickup.presentation.my_orders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityMainBinding
import com.example.gopickup.databinding.ActivityMyOrdersBinding
import com.example.gopickup.model.dummy.MyOrder
import com.example.gopickup.utils.DummyData
import com.example.gopickup.utils.showToast

class MyOrdersActivity : BaseActivity(), MyOrdersContract.View {

    private var _binding: ActivityMyOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MyOrdersPresenter

    private val myOrdersAdapter = MyOrdersAdapter {
        showToast("clicked")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MyOrdersPresenter(this)
        presenter.start()
        presenter.getMyOrderList(myOrderList = DummyData.generateMyOrders())

    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "My Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun showMyOrderList(myOrderList: List<MyOrder>?) {
        myOrderList?.let {
            myOrdersAdapter.addItems(it)

            binding.rvMyOrders.apply {
                layoutManager = LinearLayoutManager(
                    this@MyOrdersActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = myOrdersAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}