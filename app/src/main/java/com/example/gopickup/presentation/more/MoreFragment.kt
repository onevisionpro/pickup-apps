package com.example.gopickup.presentation.more

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentMoreBinding
import com.example.gopickup.model.request.Logout
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.showToast


class MoreFragment : BaseFragment(), MoreContract.View {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MorePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MorePresenter(this, callApi())
        presenter.start()

    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Info"

        binding.trackingMyOrder.setOnClickListener {
            NavigationUtils.navigateToTrackMyOrderActivity(requireActivity())
        }

        binding.aboutApps.setOnClickListener {
            NavigationUtils.navigateToAboutAppsActivity(requireActivity())
        }

        binding.btnLogout.setOnClickListener {
            val logout = Logout(devid = provideDeviceId())
            presenter.postLogout(
                BaseRequest(
                    guid = provideGUID(),
                    code = "0",
                    data = logout
                )
            )
        }
    }

    override fun showLogoutSuccess(message: String) {
        showToast(message)
        preference.clearPreference()

        NavigationUtils.navigateToLoginActivity(requireActivity())
        requireActivity().finish()
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