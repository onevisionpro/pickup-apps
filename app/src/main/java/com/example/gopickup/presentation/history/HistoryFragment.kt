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
import com.example.gopickup.databinding.FragmentHistoryBinding
import com.example.gopickup.model.dummy.History
import com.example.gopickup.utils.DummyData
import com.example.gopickup.utils.showToast


class HistoryFragment : BaseFragment(), HistoryContract.View {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HistoryPresenter

    private val historyAdapter = HistoryAdapter {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HistoryPresenter(this)
        presenter.start()
        presenter.getHistories(DummyData.generateHistories())

    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "History"

        binding.filter.setOnClickListener { showToast("clicked") }
        binding.done.setOnClickListener { showToast("clicked") }
        binding.date.setOnClickListener { showToast("clicked") }
    }

    override fun showHistories(historyList: List<History>?) {
        historyList?.let {
            historyAdapter.addItems(it)

            binding.rvHistories.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = historyAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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