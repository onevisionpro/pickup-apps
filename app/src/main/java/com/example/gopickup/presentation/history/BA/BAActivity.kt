package com.example.gopickup.presentation.history.BA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gopickup.R
import com.example.gopickup.databinding.ActivityBABinding
import com.example.gopickup.presentation.history.BA.receipt.ReceiptBAFragment
import com.example.gopickup.presentation.history.BA.shipment.ShipmentBAFragment
import com.example.gopickup.utils.ViewPagerAdapter

class BAActivity : AppCompatActivity() {

    companion object {
        const val TRACK_ID = "TRACK_ID"
        const val STATUS = "STATUS"
    }

    private var _binding: ActivityBABinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBABinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        val bundle = Bundle()
        val receiptBAFragment = ReceiptBAFragment()
        val shipmentBAFragment = ShipmentBAFragment()

        bundle.putString(TRACK_ID, intent.getStringExtra(TRACK_ID))
        receiptBAFragment.arguments = bundle
        shipmentBAFragment.arguments = bundle

        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.populateFragment(receiptBAFragment, "Pengambilan")
        pagerAdapter.populateFragment(shipmentBAFragment, "Penerimaan")

        binding.apply {
            tvToolbar.icBack.setOnClickListener { finish() }
            tvToolbar.tvToolbarTitle.text = "Berita Acara"
            viewPager.adapter = pagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}