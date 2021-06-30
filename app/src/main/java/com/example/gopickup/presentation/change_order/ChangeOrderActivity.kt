package com.example.gopickup.presentation.change_order

import android.content.Intent
import android.os.Bundle
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityChangeOrderBinding
import com.example.gopickup.presentation.main.MainActivity
import com.example.gopickup.utils.Constant
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogCancelOrderListener
import com.example.gopickup.utils.dialog.listener.IOnDialogChangeMyOrderListener
import com.example.gopickup.utils.showToast

class ChangeOrderActivity : BaseActivity(), ChangeOrderContract.View {

    private var _binding: ActivityChangeOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ChangeOrderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChangeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ChangeOrderPresenter(this)
        presenter.start()
    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Change My Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnUpdateOrder.setOnClickListener {
            DialogUtils.showDialogChangeMyOrder(this, object : IOnDialogChangeMyOrderListener {
                override fun onTrackMyOrderClicked() {
                    NavigationUtils.navigateToTrackMyOrderActivity(this@ChangeOrderActivity)
                    finish()
                }

                override fun onBackToHomeClicked() {
                    NavigationUtils.navigateToMainActivity(this@ChangeOrderActivity)
                    finish()
                }

            })
        }

        binding.btnCancelOrder.setOnClickListener {
            DialogUtils.showDialogCancelOrder(this, object : IOnDialogCancelOrderListener {
                override fun onBackToHomeClicked() {
                    NavigationUtils.navigateToMainActivity(this@ChangeOrderActivity)
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