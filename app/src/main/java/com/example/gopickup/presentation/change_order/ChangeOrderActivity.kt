package com.example.gopickup.presentation.change_order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gopickup.R
import com.example.gopickup.databinding.ActivityChangeOrderBinding
import com.example.gopickup.utils.showToast

class ChangeOrderActivity : AppCompatActivity() {

    private var _binding: ActivityChangeOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChangeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.toolbar.tvToolbarTitle.text = "Change My Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnUpdateOrder.setOnClickListener { showToast("clicked") }
        binding.btnCancel.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}