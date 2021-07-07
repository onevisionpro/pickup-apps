package com.example.gopickup.presentation.my_orders.details.partner.ba_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivitySubmitBATakeOrderBinding
import com.example.gopickup.model.request.SendOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.PreviewBA
import com.example.gopickup.utils.ImageUtils
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogSendOrderListener
import com.example.gopickup.utils.showToast
import com.github.gcacace.signaturepad.views.SignaturePad

class SubmitBATakeOrderActivity : BaseActivity(), SubmitBATakeOrderContract.View {

    companion object {
        const val TRACK_ID = "TRACK_ID"
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
            trackId = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = TrackId(trackId = intent.getStringExtra(TRACK_ID))
            )
        )
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Detail Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnDone.setOnClickListener {
            val signatureWarehouse = ImageUtils.toBase64(binding.signatureWarehouse.signatureBitmap)
            val signaturePartner = ImageUtils.toBase64(binding.signaturePartner.signatureBitmap)
            val sendOrder = SendOrder(
                trackId = intent.getStringExtra(TRACK_ID),
                ttdWarehouse = signatureWarehouse,
                ttdMitra = signaturePartner
            )
//            Log.d("TAG", "initView: $sendOrder")

            when {
                signatureWarehouse?.isEmpty()!! -> showToast("Harap tanda tangan terlebih darhulu!")
                signaturePartner?.isEmpty()!! -> showToast("Harap tanda tangan terlebih darhulu!")
                else -> {
                    presenter.postSendOrder(
                        sendOrder = BaseRequest(
                            guid = provideGUID(),
                            code = "",
                            data = sendOrder
                        )
                    )
                }
            }
        }
    }

    override fun showPreviewBA(previewBA: PreviewBA) {
        binding.webviewBa.loadDataWithBaseURL(
            null,
            previewBA.contentHtml!!,
            "text/html",
            "UTF-8",
            null
        )
    }

    override fun showSendOrderSuccess(message: String) {
        val orderId = intent.getStringExtra(TRACK_ID)
        DialogUtils.showDialogSendOrder(this, orderId!!, object : IOnDialogSendOrderListener {
            override fun onBackToHomeClicked() {
                NavigationUtils.navigateToMainActivity(this@SubmitBATakeOrderActivity)
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