package com.example.gopickup.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentProfileBinding
import com.example.gopickup.model.response.Profile
import com.example.gopickup.utils.DummyData
import com.example.gopickup.utils.showToast


class ProfileFragment : BaseFragment(), ProfileContract.View {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ProfilePresenter(this, callApi())
        presenter.start()
        presenter.getProfile(
            profileRequest = BaseRequest(
                guid = provideGUID(),
                code = "0",
                data = ""
            )
        )

    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Profile"
        binding.toolbar.icShop.setOnClickListener { showToast("clicked") }

        binding.tvEditProfile.setOnClickListener { showToast("clicked") }
    }

    override fun showProfile(profile: Profile?) {
        profile?.let {
            Glide.with(requireContext())
                .load(profile.imageProfile)
                .into(binding.imgProfile)
            binding.tvUsername.text = profile.nama
            binding.tvWarehouseName.text = profile.companyName

            binding.edtUsername.setText(profile.nama)
            binding.edtWorkPartner.setText(profile.companyName)
            binding.edtPhone.setText("08123123123")
            binding.edtEmail.setText(profile.email)
            binding.edtRole.setText(profile.msisdn)
            binding.edtConfirmPassword.setText("password")
        }

        binding.btnSave.setOnClickListener { showToast("save") }
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
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}