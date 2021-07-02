package com.example.gopickup.presentation.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentHistoryBinding
import com.example.gopickup.model.dummy.History
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.HistoryOrder
import com.example.gopickup.utils.*


class HistoryFragment : BaseFragment(), HistoryContract.View {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HistoryPresenter

    private val historyAdapter = HistoryAdapter {
        NavigationUtils.navigateToHistoryDetailsActivity(requireActivity(), trackId = it.trackId!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HistoryPresenter(this, callApi())
        presenter.start()
        presenter.getHistoriesOrder(
            historyOrderRequest = BaseRequest(
                guid = provideGUID(),
                data = TrackId(trackId = "")
            )
        )

    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "History"

        binding.filter.setOnClickListener { showToast("clicked") }
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