package com.example.gopickup.presentation.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gopickup.R
import com.example.gopickup.databinding.ActivityOrderBinding
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.showToast

class OrderActivity : AppCompatActivity() {

    private var _binding: ActivityOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        binding.toolbar.tvToolbarTitle.text = "Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.createOrder.setOnClickListener {
            NavigationUtils.navigateToCreateOrderActivity(this)
        }

        binding.myOrders.setOnClickListener {
            showToast("my orders clicked")
        }

        binding.openOrder.setOnClickListener {
            showToast("open order clicked")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}