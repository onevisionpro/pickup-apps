package com.example.gopickup.presentation.history.BA.receipt

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentReceiptBABinding
import com.example.gopickup.model.request.PreviewBARequest
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.utils.OrderStatus
import com.example.gopickup.utils.TaskManager


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
        presenter.getPreviewBA(previewBARequest = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = PreviewBARequest(
                trackId = arguments?.getString(TRACK_ID),
                type = OrderStatus.TAKE_ITEM
            )
        ))
        presenter.getBA(trackId = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = TrackId(trackId = arguments?.getString(TRACK_ID))
        ))
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)


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
        binding.btnDownloadBA.setOnClickListener {
            if( Build.VERSION.SDK_INT >= 23 )
                if( hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) )
                    if( hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ) {
                        val downloadTask = TaskManager(requireContext(), ProgressDialog(requireContext()))
                        downloadTask.execute("https://subsystem.indihome.co.id//pickup-system//bank//files//pdf//BA-RECEIVE-GID-210722337085019-210725110827.pdf")
                    }

            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
                1
            )
        }
//        if( Build.VERSION.SDK_INT >= 23 )
//            if( hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) )
//                if( hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ) {
//                    val downloadTask = TaskManager(requireContext(), ProgressDialog(requireContext()))
//                    downloadTask.execute(url)
//                    return
//                }
//
//        ActivityCompat.requestPermissions(
//            requireActivity(), arrayOf(
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        ),
//            1
//        )
//        val downloadTask = TaskManager(requireContext(), ProgressDialog(requireContext()))
//        downloadTask.execute(url)
    }

    fun hasPermission(strPerm: String?): Boolean {
        return ContextCompat.checkSelfPermission(requireActivity(), strPerm!!) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    Log.wtf("TAG", "--> Permission granted.\n")
//                    finish()
//                    startActivity(getIntent())
                } else {
                    Log.wtf("TAG", "--> Permission denied. Quitting.\n")
//                    finish()
                }
            }
        }
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
