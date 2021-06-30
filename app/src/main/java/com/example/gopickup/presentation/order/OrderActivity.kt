package com.example.gopickup.presentation.order

import android.os.Bundle
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityOrderBinding
import com.example.gopickup.utils.*

class OrderActivity : BaseActivity(), OrderContract.View {

    private var _binding: ActivityOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: OrderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OrderPresenter(this)
        presenter.start()
    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        setupUserType()

        binding.createOrder.setOnClickListener {
            NavigationUtils.navigateToCreateOrderActivity(this)
        }

        binding.myOrders.setOnClickListener {
            NavigationUtils.navigateToMyOrdersActivity(this)
        }

        binding.openOrder.setOnClickListener {
            showToast("open order clicked")
        }
    }

    private fun setupUserType() {
        when (preference.getString(Constant.KEY_USER_TYPE)) {
            UserType.MITRA -> {
                binding.myOrders.show()
                binding.openOrder.show()
            }
            UserType.WAREHOUSE -> {
                binding.createOrder.show()
                binding.myOrders.show()
                binding.openOrder.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}