package com.example.gopickup.presentation.history.BA.shipment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.R
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentShipmentBABinding
import com.example.gopickup.model.request.PreviewBARequest
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.presentation.history.BA.receipt.ReceiptBAFragment
import com.example.gopickup.utils.OrderStatus
import com.example.gopickup.utils.TaskManager


class ShipmentBAFragment : BaseFragment(), ShipmentBAContract.View {

    companion object {
        const val TRACK_ID = "TRACK_ID"
    }

    private var _binding: FragmentShipmentBABinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ShipmentBAPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ShipmentBAPresenter(this, callApi())
        presenter.start()
        presenter.getPreviewBA(previewBARequest = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = PreviewBARequest(
                trackId = arguments?.getString(ReceiptBAFragment.TRACK_ID),
                type = OrderStatus.ACCEPT_WH
            )
        ))
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)

        binding.btnDownloadBA.setOnClickListener {
            presenter.getBA(trackId = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = TrackId(trackId = arguments?.getString(ReceiptBAFragment.TRACK_ID))
            ))
        }
    }

    override fun showPreviewBA(url: String) {
        binding.webViewPreviewBA.loadDataWithBaseURL(
            null,
            url,
            "text/html",
            "UTF-8",
            null
        )
    }

    override fun showDownloadBA(url: String) {
        val downloadTask = TaskManager(requireContext(), ProgressDialog(requireContext()))
        downloadTask.execute(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShipmentBABinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}