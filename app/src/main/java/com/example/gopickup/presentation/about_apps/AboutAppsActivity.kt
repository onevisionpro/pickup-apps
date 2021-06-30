package com.example.gopickup.presentation.about_apps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gopickup.R
import com.example.gopickup.databinding.ActivityAboutAppsBinding

class AboutAppsActivity : AppCompatActivity() {

    private var _binding: ActivityAboutAppsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutAppsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.toolbar.tvToolbarTitle.text = "About Apps"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}