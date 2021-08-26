package com.example.gopickup.presentation.order

import android.os.Bundle
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
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

        presenter = OrderPresenter(this, callApi())
        presenter.start()
        presenter.getOrderCount(
            baseRequest = BaseRequest(
                guid = provideGUID(),
                code = "0",
                data = ""
            )
        )
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Pesanan"
        binding.toolbar.icBack.setOnClickListener { finish() }

        setupUserType()

        binding.createOrder.setOnClickListener {
            NavigationUtils.navigateToCreateOrderActivity(this)
        }

        binding.myOrders.setOnClickListener {
            NavigationUtils.navigateToMyOrdersActivity(this)
        }

        binding.openOrder.setOnClickListener {
            NavigationUtils.navigateToOpenOrderActivity(this)
        }
    }

    private fun setupUserType() {
        when (preference.getString(Constants.KEY_USER_TYPE)) {
            UserType.PARTNER -> {
                binding.myOrders.show()
                binding.openOrder.show()
            }
            UserType.WAREHOUSE -> {
                binding.createOrder.show()
                binding.myOrders.show()
            }
        }
    }

    override fun showMyOrderCount(count: Int) {
        if (count > 0) {
            binding.tvCountMyOrder.show()
            binding.tvCountMyOrder.text = count.toString()
        } else {
            binding.tvCountMyOrder.hide()
        }
    }

    override fun showOpenOrderCount(count: Int) {
        if (count > 0) {
            binding.tvCountOpenOrder.show()
            binding.tvCountOpenOrder.text = count.toString()
        } else {
            binding.tvCountOpenOrder.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getOrderCount(
            baseRequest = BaseRequest(
                guid = provideGUID(),
                code = "0",
                data = ""
            )
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}