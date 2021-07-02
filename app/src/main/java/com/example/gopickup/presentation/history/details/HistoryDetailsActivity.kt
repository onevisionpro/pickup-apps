package com.example.gopickup.presentation.history.details

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityHistoryDetailsBinding
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.HistoryOrderDetails
import com.example.gopickup.utils.OrderStatus

class HistoryDetailsActivity : BaseActivity(), HistoryDetailsContract.View {

    private var _binding: ActivityHistoryDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TRACK_ID = "TRACK_ID"
    }

    private lateinit var presenter: HistoryDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = HistoryDetailsPresenter(this, callApi())
        presenter.start()
        presenter.getHistoryOrderDetails(
            trackId = BaseRequest(
                guid = provideGUID(),
                data = TrackId(trackId = intent.getStringExtra(TRACK_ID))
            )
        )
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun showHistoryOrderDetails(details: HistoryOrderDetails) {
        binding.apply {
            tvWarehouseFrom.text = details.orderFrom
            tvOrderId.text = "Order ID #${details.trackId}"
            tvStatusHead.text = details.status
            when (details.status) {
                OrderStatus.FINISH -> setupStatusOrderFinish()
                OrderStatus.CANCEL -> setupStatusOrderCancel()
            }

            tvWarehouseTo.text = details.orderTo
            tvEstimateArrived.text = "estimate date"
            tvOrderIdDetails.text = details.trackId
            tvReceipt.text = details.resiCode
            tvWarehouseStatus.text = details.status
            tvNotes.text = details.notes
        }
    }

    private fun setupStatusOrderFinish() {
        binding.viewStatusColor.setBackgroundColor(
            ContextCompat.getColor(
                this@HistoryDetailsActivity,
                R.color.green
            )
        )
        binding.tvStatusHead.setTextColor(
            ContextCompat.getColor(
                this@HistoryDetailsActivity,
                R.color.green
            )
        )
    }

    private fun setupStatusOrderCancel() {
        binding.viewStatusColor.setBackgroundColor(
            ContextCompat.getColor(
                this@HistoryDetailsActivity,
                R.color.redPrimary
            )
        )
        binding.tvStatusHead.setTextColor(
            ContextCompat.getColor(
                this@HistoryDetailsActivity,
                R.color.redPrimary
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}