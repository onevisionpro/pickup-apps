package com.example.gopickup.presentation.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentProfileBinding
import com.example.gopickup.model.request.EditProfile
import com.example.gopickup.model.request.NewImage
import com.example.gopickup.model.response.Profile
import com.example.gopickup.utils.ImageUtils
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.hideKeyboard
import com.example.gopickup.utils.showToast


class ProfileFragment : BaseFragment(), ProfileContract.View {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ProfilePresenter
    private var isEditPhoto: Boolean = false
    private lateinit var newImage: NewImage

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
        binding.layoutParent.setOnClickListener { requireActivity().hideKeyboard() }
        binding.toolbar.tvToolbarTitle.text = "Profile"

        binding.tvEditPhoto.setOnClickListener {
//            setupEditable()
            isEditPhoto = true
            pickImages.launch("image/*")
        }

        binding.tvResetPassword.setOnClickListener {
            NavigationUtils.navigateToResetPasswordActivity(requireActivity())
        }
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
            binding.edtPhone.setText(profile.msisdn)
            binding.edtEmail.setText(profile.email)
            binding.edtRole.setText(profile.role)
        }

        binding.btnSave.setOnClickListener {
            if (isEditPhoto) postEditPhoto()
            else postEditProfile()
        }
    }

    override fun showEditProfileSuccess(message: String) {
        showToast(message)
        setupNotEditable()
    }

    override fun showEditPhotoProfileSuccess(message: String) {
        showToast(message)
    }

    private fun postEditPhoto() {
        presenter.postEditPhotoProfile(newImage = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = newImage
        ))
    }

    private fun postEditProfile() {
        val editProfile = EditProfile(
            name = binding.edtUsername.text.toString(),
            msisdn = binding.edtPhone.text.toString(),
            email = binding.edtEmail.text.toString()
        )
        presenter.postEditProfile(profile = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = editProfile
        ))
    }

    private fun setupEditable() {
        binding.edtUsername.isFocusableInTouchMode = true
        binding.edtUsername.isFocusable = true
        binding.edtUsername.requestFocus()

        binding.edtPhone.isFocusableInTouchMode = true
        binding.edtPhone.isFocusable = true
        binding.edtEmail.isFocusableInTouchMode = true
        binding.edtEmail.isFocusable = true
    }

    private fun setupNotEditable() {
        binding.edtUsername.isFocusableInTouchMode = false
        binding.edtUsername.isFocusable = false
        binding.edtUsername.requestFocus()

        binding.edtPhone.isFocusableInTouchMode = false
        binding.edtPhone.isFocusable = false
        binding.edtEmail.isFocusableInTouchMode = false
        binding.edtEmail.isFocusable = false
    }

    private val pickImages = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
        uri?.let { it ->
            // The image was saved into the given Uri -> do something with it
            Glide.with(requireContext())
                .load(it)
                .into(binding.imgProfile)

            val selectedImage = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
            newImage = NewImage(newImage = ImageUtils.toBase64(selectedImage))
        }
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