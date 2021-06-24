package com.example.gopickup.presentation.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.databinding.FragmentOrderBinding
import com.example.gopickup.presentation.create_order.CreateOrderFragment
import com.example.gopickup.utils.loadFragment
import com.example.gopickup.utils.navigateBack

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.toolbar.tvToolbarTitle.text = "Order"
        binding.toolbar.icBack.setOnClickListener { navigateBack() }

        binding.createOrder.setOnClickListener { loadFragment(CreateOrderFragment()) }

        binding.myOrders.setOnClickListener {

        }

        binding.openOrder.setOnClickListener {

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
        _binding = FragmentOrderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}