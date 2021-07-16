package com.example.gopickup.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.databinding.ItemRecentOrderBinding
import com.example.gopickup.model.response.RecentOrderItem
import com.example.gopickup.utils.DateUtils
import com.example.gopickup.utils.Flag
import com.example.gopickup.utils.OrderStatus

class RecentOrderAdapter(private val onItemClick: (recentOrder: RecentOrderItem) -> Unit) :
    RecyclerView.Adapter<RecentOrderAdapter.ViewHolder>() {

    private val recentOrderList = mutableListOf<RecentOrderItem>()

    fun addItems(recentOrderList: List<RecentOrderItem>) {
        this.recentOrderList.clear()
        this.recentOrderList.addAll(recentOrderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemRecentOrderBinding =
            ItemRecentOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemRecentOrderBinding)
    }

    override fun getItemCount(): Int = recentOrderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentOrder = recentOrderList[position]
        holder.bind(recentOrder)
        holder.itemView.setOnClickListener { onItemClick(recentOrder) }
    }

    inner class ViewHolder(private val binding: ItemRecentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recentOrder: RecentOrderItem) {
            with(binding) {
                tvWarehouseName.text = recentOrder.orderTo
                tvOrderId.text = recentOrder.trackId
                tvStatus.text = recentOrder.status
                tvDate.text = DateUtils.toFormatDate(recentOrder.createDtm!!)

                when (recentOrder.flag) {
                    Flag.FROM -> {
                        tvWarehouseName.text = "From ${recentOrder.orderFrom}"
                        icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_order_from))
                    }
                    Flag.TO -> {
                        tvWarehouseName.text = "To ${recentOrder.orderTo}"
                        icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_order_to))
                    }
                }

                when (recentOrder.status) {
                    OrderStatus.FINISH -> {
                        tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                    }
                    else -> {
                        tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.gold))
                    }
                }
            }
        }
    }

}