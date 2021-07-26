package com.example.gopickup.presentation.history.BA.shipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.R
import com.example.gopickup.databinding.FragmentShipmentBABinding


class ShipmentBAFragment : Fragment() {

    private var _binding: FragmentShipmentBABinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        _binding = FragmentShipmentBABinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}