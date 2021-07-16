package com.example.gopickup.presentation.history.filter

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gopickup.R
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentHistoryFilterBinding
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.HistoryOrderRequest
import com.example.gopickup.network.ApiClient
import com.example.gopickup.network.ApiRest
import com.example.gopickup.presentation.history.HistoryFragment
import com.example.gopickup.utils.*
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnItemClicked
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class HistoryFilterFragment : BottomSheetDialogFragment(), HistoryFilterContract.View {

    private var _binding: FragmentHistoryFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetFilterHistory: BottomSheetDialog

    private lateinit var presenter: HistoryFilterPresenter
    private lateinit var preference: SharedPreference
    private var historyOrderRequest = HistoryOrderRequest()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val service = ApiClient().getClient().create(ApiRest::class.java)
        val request =  AppRepositoryImpl(service)
        preference = SharedPreference(requireContext())

        presenter = HistoryFilterPresenter(this, request)
        presenter.start()
        presenter.getStatusList(baseRequest = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = ""
        ))

    }

    override fun initView() {
        binding.layoutParent.setOnClickListener { requireActivity().hideKeyboard() }

        val currentDate = DateUtils.formatCurrentDate(Calendar.getInstance().time)
        binding.edtDateStart.setText(currentDate)
        binding.edtDateEnd.setText(currentDate)
    }

    override fun showStatusList(statusList: List<String>) {
        binding.edtStatus.setOnClickListener {
            DialogUtils.showDialogStatus(requireContext(), statusList,
                object : IOnItemClicked<String> {
                    override fun onItemClicked(data: String) {
                        historyOrderRequest.status = data
                        binding.edtStatus.setText(data)
                    }

                })
        }

        binding.edtDateStart.setOnClickListener {
            DialogUtils.showDialogCalendar(requireContext(), object : IOnItemClicked<Date> {
                override fun onItemClicked(data: Date) {
                    binding.edtDateStart.setText(DateUtils.formatDate(data))
                    historyOrderRequest.startDtm = DateUtils.formatDate(data)
                }

            })
        }

        binding.edtDateEnd.setOnClickListener {
            DialogUtils.showDialogCalendar(requireContext(), object : IOnItemClicked<Date> {
                override fun onItemClicked(data: Date) {
                    binding.edtDateEnd.setText(DateUtils.formatDate(data))
                    historyOrderRequest.endDtm = DateUtils.formatDate(data)
                }

            })
        }

        binding.btnApply.setOnClickListener {
            historyOrderRequest.trackId = binding.edtOrderId.text.toString()
            Log.d("TAG", "showStatusList: $historyOrderRequest")
            bottomSheetFilterHistory.behavior.state = BottomSheetBehavior.STATE_HIDDEN
            loadFragment(HistoryFragment())
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

    private fun loadFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putParcelable("data", historyOrderRequest)
        fragment.arguments = bundle
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.main_container, fragment)
            ?.addToBackStack(fragment.javaClass.name)
            ?.commit()
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