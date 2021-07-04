package com.example.gopickup.presentation.history.details

import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityHistoryDetailsBinding
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.HistoryOrderDetails
import com.example.gopickup.model.response.ItemOrder
import com.example.gopickup.model.response.ItemWarehouse
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
            setupItemsLayout(details.items)
            tvEstimateArrived.text = "estimate date"
            tvOrderIdDetails.text = details.trackId
            tvReceipt.text = details.resiCode
            tvWarehouseStatus.text = details.status
            tvNotes.text = details.notes
        }
    }

    private fun setupItemsLayout(items: List<ItemOrder>?) {
        val itemOrderAdapter = ItemOrderAdapter()

        items?.let {
            itemOrderAdapter.addItems(it)
            binding.rvItems.apply {
                layoutManager = LinearLayoutManager(
                    this@HistoryDetailsActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = itemOrderAdapter
            }
            binding.rvItems.isNestedScrollingEnabled = false
        }
    }

    private fun setupStatusOrderFinish() {
        binding.viewStatusColor.background = ContextCompat.getDrawable(
            this@HistoryDetailsActivity,
            R.drawable.view_circle_green
        )
        binding.tvStatusHead.setTextColor(
            ContextCompat.getColor(
                this@HistoryDetailsActivity,
                R.color.green
            )
        )
    }

    private fun setupStatusOrderCancel() {
        binding.viewStatusColor.background = ContextCompat.getDrawable(
            this@HistoryDetailsActivity,
            R.drawable.view_circle_red
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