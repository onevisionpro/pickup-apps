package com.example.gopickup.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.databinding.ItemRecentOrderBinding
import com.example.gopickup.model.dummy.RecentOrder

class RecentOrderAdapter(private val onItemClick: (recentOrder: RecentOrder) -> Unit) :
    RecyclerView.Adapter<RecentOrderAdapter.ViewHolder>() {

    private val recentOrderList = mutableListOf<RecentOrder>()

    fun addItems(recentOrderList: List<RecentOrder>) {
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
    }

    inner class ViewHolder(private val binding: ItemRecentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recentOrder: RecentOrder) {
            with(binding) {
                tvWarehouseName.text = recentOrder.warehouseName
                tvOrderId.text = recentOrder.orderId
                tvStatus.text = recentOrder.statusDesc
                tvDate.text = recentOrder.date

                when (recentOrder.status) {
                    "Selesai" -> {
                        viewStatusColor.background = ContextCompat.getDrawable(itemView.context, R.drawable.view_circle_green)
                        tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                    }
                    else -> {
                        viewStatusColor.background = ContextCompat.getDrawable(itemView.context, R.drawable.view_circle_gold)
                        tvStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.gold))
                    }
                }
            }
        }
    }

}