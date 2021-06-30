package com.example.gopickup.presentation.track_my_order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityTrackMyOrderBinding

class TrackMyOrderActivity : BaseActivity(), TrackMyOrderContract.View {

    private var _binding: ActivityTrackMyOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: TrackMyOrderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTrackMyOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = TrackMyOrderPresenter(this)
        presenter.start()
    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Track My Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}