package com.example.gopickup.presentation.my_orders.filter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.R
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentMyOrderFilterBinding
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.network.ApiClient
import com.example.gopickup.network.ApiRest
import com.example.gopickup.presentation.history.filter.HistoryFilterPresenter
import com.example.gopickup.presentation.my_orders.MyOrdersActivity
import com.example.gopickup.utils.*
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnItemClicked
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MyOrderFilterFragment : BottomSheetDialogFragment(), MyOrderFilterContract.View {

    private var _binding: FragmentMyOrderFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetFilterMyOrder: BottomSheetDialog

    private lateinit var presenter: MyOrderFilterPresenter
    private lateinit var preference: SharedPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val service = ApiClient().getClient().create(ApiRest::class.java)
        val request =  AppRepositoryImpl(service)
        preference = SharedPreference(requireContext())

        presenter = MyOrderFilterPresenter(this, request)
        presenter.start()
        presenter.getStatusList(baseRequest = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = ""
        )
        )
    }

    override fun initView() {
        binding.layoutParent.setOnClickListener { requireActivity().hideKeyboard() }
    }

    override fun showStatusList(statusList: List<String>) {
        val intent = Intent(requireActivity(), MyOrdersActivity::class.java)

        binding.edtStatus.setOnClickListener {
            DialogUtils.showDialogStatus(requireContext(), statusList,
                object : IOnItemClicked<String> {
                    override fun onItemClicked(data: String) {
                        binding.edtStatus.setText(data)
                        intent.putExtra("status", data)
                    }

                })
        }

        binding.btnApply.setOnClickListener {
            intent.putExtra("track_id", binding.edtOrderId.text.toString())

            startActivity(intent)
            requireActivity().finish()
        }

    }

    override fun showLoading() {
        binding.progressBar.show()
    }

    override fun hideLoading() {
        binding.progressBar.hide()
    }

    override fun showMessage(message: String?) {
        showToast(message!!)
    }

    override fun showSessionExpired(message: String?) {
        showToast(message!!)
    }

    fun provideGUID(): String {
        val token = preference.getString(Constants.KEY_TOKEN)
        val devId = provideDeviceId()
        return StringUtils.toMd5("$token$devId")
    }

    @SuppressLint("HardwareIds")
    fun provideDeviceId(): String {
        return Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetFilterMyOrder = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        _binding = FragmentMyOrderFilterBinding.inflate(layoutInflater, null, false)
        bottomSheetFilterMyOrder.setContentView(binding.root)

        bottomSheetFilterMyOrder.setOnShowListener {
            val castDialog = it as BottomSheetDialog
            val bottomSheet = castDialog.findViewById<View?>(R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return bottomSheetFilterMyOrder
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
        _binding = FragmentMyOrderFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}