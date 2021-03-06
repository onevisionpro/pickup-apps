package com.example.gopickup.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
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


class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HomePresenter

    private val itemsAdapter = ItemsAdapter {

    }

    private val recentOrderAdapter = RecentOrderAdapter {
        when (preference.getString(Constants.KEY_USER_TYPE)) {
            UserType.WAREHOUSE -> {
                if (preference.getString(Constants.KEY_COMPANY_NAME).equals(it.orderFrom)) {
                    when (it.status) {
                        OrderStatus.ORDER_CREATED -> {
                            NavigationUtils.navigateToChangeOrderActivity(requireActivity(), it.trackId!!)
                        }
                        OrderStatus.BOOKED -> {
                            NavigationUtils.navigateToChangeOrderActivity(requireActivity(), it.trackId!!)
                        }
                        OrderStatus.TAKE_ITEM -> {
                            NavigationUtils.navigateToChangeOrderActivity(requireActivity(), it.trackId!!)
                        }
                        else -> {
                            NavigationUtils.navigateToMyOrderDetailsWarehouseActivity(requireActivity(), it.trackId!!)
                        }
                    }
                } else {
                    NavigationUtils.navigateToMyOrderDetailsWarehouseActivity(requireActivity(), it.trackId!!)
                }
            }
            UserType.PARTNER -> {
                when (it.status) {
                    OrderStatus.BOOKED -> {
                        NavigationUtils.navigateToMyOrderDetailsTakeOrderActivity(requireActivity(), it.trackId!!)
                    }
                    OrderStatus.TAKE_ITEM -> {
                        NavigationUtils.navigateToSubmitBAOrderActivity(
                            requireActivity(),
                            trackId =  it.trackId!!,
                            warehouseName = it.orderTo!!,
                            status = it.status
                        )
                    }
                    OrderStatus.ACCEPT_WH -> {
                        NavigationUtils.navigateToMyOrderDetailsReceivedOrderActivity(requireActivity(), it.trackId!!)
                    }
                    OrderStatus.ARRIVED -> {
                            NavigationUtils.navigateToSubmitBAOrderActivity(
                                requireActivity(),
                                it.trackId!!,
                                it.orderTo!!,
                                it.status
                            )
                    }
                    else -> {
                        NavigationUtils.navigateToMyOrderDetailsWarehouseActivity(requireActivity(), it.trackId!!)
                    }
                }
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
        binding.swipeRefresh.setOnRefreshListener(this)

        binding.tvSeeAllRecentOrdersItems.setOnClickListener {
            NavigationUtils.navigateToMyOrdersActivity(requireActivity())
        }
    }

    override fun showVersionChecker(versionChecker: VersionChecker) {
        val currentVersion = BuildConfig.VERSION_NAME
        if (currentVersion != versionChecker.updatedVersion) {
            if (versionChecker.pushUpdate == PushUpdateStatus.YES) {
                DialogUtils.showDialogNewUpdateVersion(requireContext(), versionChecker.updatedVersion!!,
                    object : IOnDialogUpdateVersionListener {
                        override fun onUpdateClicked() {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(versionChecker.urlApps)
                            startActivity(intent)
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
        binding.toolbar.imgCompany.show()
        binding.toolbar.tvToolbarTitle.text = setGreetingMessage()
        profile?.let {
            Glide.with(requireActivity())
                .load(it.imageProfile)
                .into(binding.toolbar.imgProfile)
            binding.toolbar.tvName.text = it.nama
            binding.toolbar.tvCompanyName.text = it.companyName

            preference.saveString(Constants.KEY_COMPANY_NAME, it.companyName!!)
        }
    }

    private fun setGreetingMessage(): String {
        val c = Calendar.getInstance()

        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Selamat Pagi"
            in 12..15 -> "Selamat Siang"
            in 16..19 -> "Selamat Sore"
            in 20..23 -> "Selamat Malam"
            else -> "Hallo"
        }
    }

    override fun onRefresh() {
        presenter.getRecentOrderItems(
            BaseRequest(
                guid = provideGUID(),
                data = RecentOrder(limit = "5")
            )
        )
        binding.swipeRefresh.isRefreshing = false
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