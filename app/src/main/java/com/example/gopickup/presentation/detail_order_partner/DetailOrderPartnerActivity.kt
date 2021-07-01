package com.example.gopickup.presentation.detail_order_partner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.databinding.ActivityDetailOrderPartnerBinding
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogOrderBookedListener
import com.example.gopickup.utils.showToast

class DetailOrderPartnerActivity : BaseActivity(), DetailOrderPartnerContract.View {

    private var _binding: ActivityDetailOrderPartnerBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: DetailOrderPartnerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailOrderPartnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DetailOrderPartnerPresenter(this)
        presenter.start()

    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnBookOrder.setOnClickListener {
            DialogUtils.showDialogOrderBooked(this, object : IOnDialogOrderBookedListener {
                override fun onBackToHomeClicked() {
                    NavigationUtils.navigateToMainActivity(this@DetailOrderPartnerActivity)
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