package com.example.gopickup.presentation.my_orders.details.warehouse

import android.os.Bundle
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityMyOrderDetailsBinding

class MyOrderDetailsActivity : BaseActivity(), MyOrderDetailsContract.View {

    private var _binding: ActivityMyOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MyOrderDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MyOrderDetailsPresenter(this)
        presenter.start()
    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnTakeItem.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}