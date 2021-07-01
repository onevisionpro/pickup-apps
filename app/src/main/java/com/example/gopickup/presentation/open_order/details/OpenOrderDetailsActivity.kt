package com.example.gopickup.presentation.open_order.details

import android.os.Bundle
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityOpenOrderBinding
import com.example.gopickup.databinding.ActivityOpenOrderDetailsBinding
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogOrderBookedListener

class OpenOrderDetailsActivity : BaseActivity(), OpenOrderDetailsContract.View {

    private var _binding: ActivityOpenOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: OpenOrderDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOpenOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OpenOrderDetailsPresenter(this)
        presenter.start()

    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnBookOrder.setOnClickListener {
            DialogUtils.showDialogOrderBooked(this, object : IOnDialogOrderBookedListener {
                override fun onBackToHomeClicked() {
                    NavigationUtils.navigateToMainActivity(this@OpenOrderDetailsActivity)
                    finish()
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}