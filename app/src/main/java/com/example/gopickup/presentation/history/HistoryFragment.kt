package com.example.gopickup.presentation.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentHistoryBinding
import com.example.gopickup.model.request.HistoryOrderRequest
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.HistoryOrder
import com.example.gopickup.presentation.history.filter.HistoryFilterFragment
import com.example.gopickup.utils.*


class HistoryFragment : BaseFragment(), HistoryContract.View {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HistoryPresenter
    private var historyOrderRequest =  HistoryOrderRequest()

    private val historyAdapter = HistoryAdapter {
        NavigationUtils.navigateToHistoryDetailsActivity(requireActivity(), trackId = it.trackId!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HistoryPresenter(this, callApi())
        presenter.start()
        if (arguments == null) getDataWithoutFilter()
        else getDataWithFilter(arguments)

    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "History"

        binding.filter.setOnClickListener {
            val bottomSheetFilter =
                HistoryFilterFragment()
            bottomSheetFilter.show(activity?.supportFragmentManager!!, bottomSheetFilter.tag)
        }
        binding.done.setOnClickListener { showToast("clicked") }
        binding.date.setOnClickListener { showToast("clicked") }
    }

    override fun showHistoriesOrder(historyOrderList: List<HistoryOrder>?) {
        historyOrderList?.let {
            if (it.isNotEmpty()) {
                historyAdapter.addItems(it)

                binding.rvHistories.apply {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        RecyclerView.VERTICAL,
                        false
                    )
                    adapter = historyAdapter
                }
            } else {
                binding.tvNoHistoryOrderItems.show()
                binding.rvHistories.hide()
            }
        }
    }

    private fun getDataWithoutFilter() {
        presenter.getHistoriesOrder(
            historyOrderRequest = BaseRequest(
                guid = provideGUID(),
                data = historyOrderRequest
            )
        )
    }

    private fun getDataWithFilter(bundle: Bundle?) {
        historyOrderRequest = bundle?.getParcelable("data")!!
        presenter.getHistoriesOrder(
            historyOrderRequest = BaseRequest(
                guid = provideGUID(),
                data = historyOrderRequest
            )
        )


        if (!historyOrderRequest.status.equals("")) {
            binding.layoutStatus.show()
            binding.tvStatus.text = historyOrderRequest.status
        }
        if (!historyOrderRequest.startDtm.equals("") && !historyOrderRequest.endDtm.equals("")) {
            binding.layoutDateRange.show()
            binding.tvDateRange.text = "${historyOrderRequest.startDtm}-${historyOrderRequest.endDtm}"
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
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}