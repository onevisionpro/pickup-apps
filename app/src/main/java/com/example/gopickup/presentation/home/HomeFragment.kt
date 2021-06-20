package com.example.gopickup.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseView
import com.example.gopickup.databinding.FragmentHomeBinding
import com.example.gopickup.model.dummy.Item
import com.example.gopickup.model.dummy.RecentOrder
import com.example.gopickup.utils.DummyData


class HomeFragment : BaseFragment(), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HomePresenter

    private val itemsAdapter = ItemsAdapter {

    }

    private val recentOrderAdapter = RecentOrderAdapter {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenter(this)
        presenter.start()

    }

    override fun initView() {
        super.initView()
        presenter.getItems(DummyData.generateItems())
        presenter.getRecentOrderItems(DummyData.generateRecentOrderItems())
    }

    override fun showItems(items: List<Item>?) {
        items?.let {
            itemsAdapter.addItems(it)

            binding.rvItems.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    RecyclerView.HORIZONTAL,
                    false
                )
                adapter = itemsAdapter
            }
        }
    }

    override fun showRecentOrderItems(recentOrderItems: List<RecentOrder>?) {
        recentOrderItems?.let {
            recentOrderAdapter.addItems(it)

            binding.rvRecentOrdersItems.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = recentOrderAdapter
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
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}