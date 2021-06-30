package com.example.gopickup.presentation.detail_order_partner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gopickup.R
import com.example.gopickup.databinding.ActivityDetailOrderPartnerBinding
import com.example.gopickup.utils.showToast

class DetailOrderPartnerActivity : AppCompatActivity() {

    private var _binding: ActivityDetailOrderPartnerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailOrderPartnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnBookOrder.setOnClickListener { showToast("clicked") }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}