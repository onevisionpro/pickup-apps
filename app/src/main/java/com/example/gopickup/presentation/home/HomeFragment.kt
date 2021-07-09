package com.example.gopickup.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.BuildConfig
import com.example.gopickup.base.BaseFragment
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.FragmentHomeBinding
import com.example.gopickup.model.request.RecentOrder
import com.example.gopickup.model.response.*
import com.example.gopickup.utils.*
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogUpdateVersionListener
import java.util.*


class HomeFragment : BaseFragment(), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HomePresenter

    private val itemsAdapter = ItemsAdapter {

    }

    private val recentOrderAdapter = RecentOrderAdapter {
        when (it.status) {
            OrderStatus.BOOKED -> {
                NavigationUtils.navigateToChangeOrderActivity(
                    requireActivity(),
                    it.trackId!!)
            }
            else -> {
                NavigationUtils.navigateToMyOrderDetailsWarehouseActivity(
                    requireActivity(),
                    it.trackId!!
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenter(this, callApi())
        presenter.start()
        presenter.postVersionChecker(
            baseRequest = BaseRequest(
                guid = "OVP2021#PickUpMobile",
                code = "0",
                data = ""
            )
        )
        presenter.getHomeInformation(
            baseRequest = BaseRequest(
                guid = provideGUID(),
                code = "0",
                data = ""
            )
        )
        presenter.getRecentOrderItems(
            BaseRequest(
                guid = provideGUID(),
                data = RecentOrder(limit = "5")
            )
        )
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
        binding.toolbar.tvToolbarTitle.text = setGreetingMessage()

        binding.tvSeeAllRecentOrdersItems.setOnClickListener {
            NavigationUtils.navigateToMyOrdersActivity(requireActivity())
        }
    }

    override fun showVersionChecker(versionChecker: VersionChecker) {
        val versionName = BuildConfig.VERSION_NAME
        if (versionName != versionChecker.updatedVersion) {
            if (versionChecker.pushUpdate == PushUpdateStatus.YES) {
                DialogUtils.showDialogNewUpdateVersion(requireContext(), versionChecker.updatedVersion!!,
                    object : IOnDialogUpdateVersionListener {
                        override fun onUpdateClicked() {
                            showToast("clicked")
                        }

                    })
            }
        }
    }

    override fun showBanners(bannerList: List<Banner>?) {
        bannerList?.let {
            val bannerAdapter = TopBannerAdapter(requireContext(), it)
            binding.viewPager.apply {
                adapter = bannerAdapter
            }
        }
    }

    override fun showItems(itemList: List<Item>?) {
        itemList?.let {
            itemsAdapter.addItems(it)

            binding.rvItems.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    RecyclerView.HORIZONTAL,
                    false
                )
                adapter = itemsAdapter
            }
        }
    }

    override fun showRecentOrderItems(recentOrderItems: List<RecentOrderItem>?) {
        recentOrderItems?.let {
            if (it.isNotEmpty()) {
                recentOrderAdapter.addItems(it)

                binding.rvRecentOrdersItems.apply {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        RecyclerView.VERTICAL,
                        false
                    )
                    adapter = recentOrderAdapter
                }
            } else {
                binding.tvNoRecentOrderItems.show()
                binding.rvRecentOrdersItems.hide()
            }
        }
    }

    override fun showProfile(profile: Profile?) {
        profile?.let {
            binding.toolbar.tvCompanyName.text = it.companyName
        }
    }

    private fun setGreetingMessage(): String {
        val c = Calendar.getInstance()

        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..13 -> "Good Morning"
            in 14..17 -> "Good Afternoon"
            in 18..23 -> "Selamat Evening"
            else -> "Halo"
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
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}