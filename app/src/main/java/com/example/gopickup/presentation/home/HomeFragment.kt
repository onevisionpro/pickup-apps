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
import com.example.gopickup.model.response.Banner
import com.example.gopickup.model.response.Item
import com.example.gopickup.model.response.RecentOrderItem
import com.example.gopickup.model.response.VersionChecker
import com.example.gopickup.utils.PushUpdateStatus
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogUpdateVersionListener
import com.example.gopickup.utils.showToast


class HomeFragment : BaseFragment(), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HomePresenter

    private val itemsAdapter = ItemsAdapter {

    }

    private val recentOrderAdapter = RecentOrderAdapter {

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
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
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
            recentOrderAdapter.addItems(it)

            binding.rvRecentOrdersItems.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = recentOrderAdapter
            }
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