package com.example.gopickup.presentation.create_order

import android.content.Intent
import android.os.Bundle
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityCreateOrderBinding
import com.example.gopickup.presentation.history.HistoryFragment
import com.example.gopickup.presentation.main.MainActivity
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.IOnDialogCreateOrderClicked
import com.example.gopickup.utils.loadFragment

class CreateOrderActivity : BaseActivity(), CreateOrderContract.View {

    private var _binding: ActivityCreateOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: CreateOrderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CreateOrderPresenter(this)
        presenter.start()
    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Create Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnOrder.setOnClickListener {
            DialogUtils.showDialogCreateOrder(this, object : IOnDialogCreateOrderClicked {
                override fun onHistoryOrderClicked() {
                    val intent = Intent(this@CreateOrderActivity, MainActivity::class.java)
                    intent.putExtra("NAVIGATE_TO", "HISTORY")
                    startActivity(intent)
                    finish()
                }

                override fun onBackToHomeClicked() {
                    NavigationUtils.navigateToMainActivity(this@CreateOrderActivity)
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