package com.example.gopickup.presentation.create_order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gopickup.R
import com.example.gopickup.databinding.ActivityCreateOrderBinding

class CreateOrderActivity : AppCompatActivity() {

    private var _binding: ActivityCreateOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.toolbar.tvToolbarTitle.text = "Create Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}