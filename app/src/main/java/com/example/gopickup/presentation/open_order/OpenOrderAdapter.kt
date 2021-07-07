package com.example.gopickup.presentation.open_order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.databinding.ItemOpenOrderBinding
import com.example.gopickup.model.response.Order
import com.example.gopickup.utils.DateUtils
import com.example.gopickup.utils.OrderStatus

class OpenOrderAdapter(private val onItemClick: (order: Order) -> Unit) :
    RecyclerView.Adapter<OpenOrderAdapter.ViewHolder>() {

    private val orderList = mutableListOf<Order>()

    fun addItems(myOrderList: List<Order>) {
        this.orderList.clear()
        this.orderList.addAll(myOrderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemOpenOrderBinding =
            ItemOpenOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemOpenOrderBinding)
    }

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order)
        holder.itemView.setOnClickListener { onItemClick(order) }
    }

    inner class ViewHolder(private val binding: ItemOpenOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myOrder: Order) {
            with(binding) {
                tvWarehouseName.text = myOrder.orderTo
                tvEstimate.text = myOrder.estimateArrival
                tvOrderId.text = "Order ID #${myOrder.trackId}"
                tvDate.text = DateUtils.toFormatDate(myOrder.createDtm!!)

                viewStatusColor.background = when (myOrder.status) {
                    OrderStatus.FINISH -> {
                        ContextCompat.getDrawable(itemView.context, R.drawable.view_circle_green)
                    }
                    OrderStatus.CANCEL -> {
                        ContextCompat.getDrawable(itemView.context, R.drawable.view_circle_red)
                    }
                    else -> {
                        ContextCompat.getDrawable(itemView.context, R.drawable.view_circle_gold)
                    }
                }
            }
        }
    }

}