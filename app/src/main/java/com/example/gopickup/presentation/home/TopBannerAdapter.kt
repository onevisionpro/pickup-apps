package com.example.gopickup.presentation.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.gopickup.databinding.ItemBannerBinding
import com.example.gopickup.model.response.Banner

class TopBannerAdapter(private val context: Context, private val bannerList: List<Banner>) :
    PagerAdapter() {

    override fun isViewFromObject(view: View, o: Any): Boolean = view == o

    override fun getCount(): Int = bannerList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        try {
            val banner: Banner = bannerList[position]
            val itemBannerBinding = ItemBannerBinding.inflate(LayoutInflater.from(context))

            Glide.with(context)
                .load(banner.image)
                .into(itemBannerBinding.imgBanner)

            container.addView(itemBannerBinding.root)
            return itemBannerBinding.root
        } catch (e: Exception) {
            Log.e("BannerAdapterErr", e.localizedMessage!!)
        }

        return container
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}