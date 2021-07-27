package com.example.gopickup.presentation.history.BA.receipt

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.esafirm.rxdownloader.RxDownloader
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentReceiptBABinding
import com.example.gopickup.model.request.PreviewBARequest
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.utils.*


class ReceiptBAFragment : BaseFragment(), ReceiptBAContract.View {

    companion object {
        const val TRACK_ID = "TRACK_ID"
    }

    private var _binding: FragmentReceiptBABinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ReceiptBAPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ReceiptBAPresenter(this, callApi())
        presenter.start()
        presenter.getGeneratedBA(
            trackId = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = TrackId(trackId = arguments?.getString(TRACK_ID))
            )
        )
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
    }

    override fun showGeneratedBA(url: String) {
        if (url != "") {
            val finalUrl = "http://docs.google.com/viewer?url=$url&embedded=true"
            binding.webViewPreviewBA.settings.javaScriptEnabled = true
            binding.webViewPreviewBA.webViewClient = AppWebViewClients(binding.progressBar)
            binding.webViewPreviewBA.loadUrl(finalUrl)

            binding.btnDownloadBA.setOnClickListener {
                if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        downloadFile(url)
                    }
                }

                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                    1
                )
            }
        } else {
            binding.webViewPreviewBA.hide()
            binding.tvNoBa.show()
            binding.btnDownloadBA.hide()
        }
    }

    override fun showNoBA(message: String) {
        binding.tvNoBa.show()
        binding.tvNoBa.text = message

        binding.webViewPreviewBA.hide()
        binding.btnDownloadBA.hide()
    }

    private fun hasPermission(strPerm: String?): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            strPerm!!
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    showToast("Disetujui")
                } else {
                    showToast("Tidak Disetujui")
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun downloadFile(url: String) {
        val fileName = url.substringAfter("pdf/")
        RxDownloader(requireContext())
            .download(
                url,
                fileName,
                true
            ) // url, filename, and mimeType
            .subscribe(
                { path -> showToast("Downloading $fileName") },
                { throwable ->
                    Log.d(
                        "DOWNLOADER",
                        "ERROR: ${throwable.localizedMessage}"
                    )
                })
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
        _binding = FragmentReceiptBABinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
