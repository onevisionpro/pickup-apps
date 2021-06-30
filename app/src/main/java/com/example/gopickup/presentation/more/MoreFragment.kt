package com.example.gopickup.presentation.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.databinding.FragmentMoreBinding
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.showToast


class MoreFragment : BaseFragment(), MoreContract.View {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MorePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MorePresenter(this)
        presenter.start()

    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "More"

        binding.trackingMyOrder.setOnClickListener {
            NavigationUtils.navigateToTrackMyOrderActivity(requireActivity())
        }

        binding.aboutApps.setOnClickListener {
            NavigationUtils.navigateToAboutAppsActivity(requireActivity())
        }

        binding.btnLogout.setOnClickListener {
            NavigationUtils.navigateToLoginActivity(requireActivity())
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
        _binding = FragmentMoreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}