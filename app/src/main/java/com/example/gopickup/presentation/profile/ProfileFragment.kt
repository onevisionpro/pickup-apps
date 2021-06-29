package com.example.gopickup.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.R
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.databinding.FragmentProfileBinding
import com.example.gopickup.model.dummy.Profile
import com.example.gopickup.utils.DummyData
import com.example.gopickup.utils.showToast


class ProfileFragment : BaseFragment(), ProfileContract.View {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ProfilePresenter(this)
        presenter.start()
        presenter.getProfile(DummyData.generateDummyProfile())

    }

    override fun initView() {
        super.initView()
        binding.toolbar.tvToolbarTitle.text = "Profile"
        binding.toolbar.icShop.setOnClickListener { showToast("clicked") }

        binding.tvEditProfile.setOnClickListener { showToast("clicked") }
    }

    override fun showProfile(profile: Profile) {
        binding.edtUsername.setText(profile.name)
        binding.edtWorkPartner.setText(profile.mitraKerja)
        binding.edtPhone.setText(profile.phone)
        binding.edtEmail.setText(profile.email)
        binding.edtRole.setText(profile.role)
        binding.edtConfirmPassword.setText(profile.confirmPassword)

        binding.btnSave.setOnClickListener { showToast("save") }
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
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}