package com.example.gopickup.presentation.create_order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.R
import com.example.gopickup.databinding.FragmentCreateOrderBinding
import com.example.gopickup.utils.navigateBack


class CreateOrderFragment : Fragment() {

    private var _binding: FragmentCreateOrderBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.toolbar.tvToolbarTitle.text = "Create Order"
        binding.toolbar.icBack.setOnClickListener { navigateBack() }
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
        _binding = FragmentCreateOrderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}