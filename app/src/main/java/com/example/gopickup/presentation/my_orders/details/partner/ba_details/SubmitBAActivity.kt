package com.example.gopickup.presentation.my_orders.details.partner.ba_details

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivitySubmitBATakeOrderBinding
import com.example.gopickup.model.request.FinishOrder
import com.example.gopickup.model.request.PreviewBARequest
import com.example.gopickup.model.request.SendOrder
import com.example.gopickup.model.response.PreviewBA
import com.example.gopickup.utils.ImageUtils
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.OrderStatus
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogReceivedOrderListener
import com.example.gopickup.utils.dialog.listener.IOnDialogSendOrderListener
import com.example.gopickup.utils.showToast

class SubmitBAActivity : BaseActivity(), SubmitBATakeOrderContract.View {

    companion object {
        const val WH_NAME = "WH_NAME"
        const val TRACK_ID = "TRACK_ID"
        const val STATUS = "STATUS"
    }

    private var _binding: ActivitySubmitBATakeOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: SubmitBATakeOrderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySubmitBATakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SubmitBATakeOrderPresenter(this, callApi())
        presenter.start()
        presenter.getPreviewBA(
            previewBA = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = PreviewBARequest(
                    trackId = intent.getStringExtra(TRACK_ID),
                    type = intent.getStringExtra(STATUS)
                )
            )
        )
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }
        binding.tvWarehouseName.text = intent.getStringExtra(WH_NAME)
        binding.tvOrderId.text = "Order ID#${intent.getStringExtra(TRACK_ID)}"

        binding.btnDone.setOnClickListener {
            val signatureWarehouse = ImageUtils.toBase64(binding.signatureWarehouse.signatureBitmap)
            val signaturePartner = ImageUtils.toBase64(binding.signaturePartner.signatureBitmap)

            when {
                signatureWarehouse?.isEmpty()!! -> showToast("Harap tanda tangan terlebih darhulu!")
                signaturePartner?.isEmpty()!! -> showToast("Harap tanda tangan terlebih darhulu!")
                else -> {
                    when (intent.getStringExtra(STATUS)) {
                        OrderStatus.TAKE_ITEM -> {
                            val sendOrder = SendOrder(
                                trackId = intent.getStringExtra(TRACK_ID),
                                ttdWarehouse = signatureWarehouse,
                                ttdMitra = signaturePartner
                            )
                            presenter.postSendOrder(
                                sendOrder = BaseRequest(
                                    guid = provideGUID(),
                                    code = "",
                                    data = sendOrder
                                )
                            )
                        }
                        OrderStatus.ARRIVED -> {
                            val finishOrder = FinishOrder(
                                trackId = intent.getStringExtra(TRACK_ID),
                                ttdWarehouse = signatureWarehouse,
                                ttdMitra = signaturePartner
                            )
                            presenter.postFinishOrder(
                                finishOrder = BaseRequest(
                                    guid = provideGUID(),
                                    code = "",
                                    data = finishOrder
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    override fun showPreviewBA(previewBA: PreviewBA) {
        Glide.with(this)
            .load(previewBA.pdf_icon)
            .into(binding.imgPreviewBa)

        binding.tvPreviewBa.setOnClickListener {
            DialogUtils.showDialogBAPreview(this, previewBA.contentHtml!!)
        }
    }

    override fun showSendOrderSuccess(message: String) {
        val orderId = intent.getStringExtra(TRACK_ID)
        DialogUtils.showDialogSendOrder(this, orderId!!, object : IOnDialogSendOrderListener {
            override fun onBackToHomeClicked() {
                NavigationUtils.navigateToMainActivity(this@SubmitBAActivity)
                finish()
            }

        })
    }

    override fun showFinishOrderSuccess(message: String) {
        val orderId = intent.getStringExtra(TRACK_ID)
        DialogUtils.showDialogReceivedOrder(this, orderId!!, object : IOnDialogReceivedOrderListener {
            override fun onBackToHomeClicked() {
                NavigationUtils.navigateToMainActivity(this@SubmitBAActivity)
                finish()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}