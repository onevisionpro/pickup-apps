package com.example.gopickup.presentation.history

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.R
import com.example.gopickup.databinding.FragmentHistoryFilterBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HistoryFilterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentHistoryFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetFilterHistory: BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetFilterHistory = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        _binding = FragmentHistoryFilterBinding.inflate(layoutInflater, null, false)
        bottomSheetFilterHistory.setContentView(binding.root)

        bottomSheetFilterHistory.setOnShowListener {
            val castDialog = it as BottomSheetDialog
            val bottomSheet = castDialog.findViewById<View?>(R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return bottomSheetFilterHistory
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
        _binding = FragmentHistoryFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}