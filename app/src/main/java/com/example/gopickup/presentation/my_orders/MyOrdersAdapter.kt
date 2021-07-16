package com.example.gopickup.presentation.my_orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.databinding.ItemMyOrderBinding
import com.example.gopickup.model.response.Order
import com.example.gopickup.utils.DateUtils
import com.example.gopickup.utils.Flag
import com.example.gopickup.utils.OrderStatus

class MyOrdersAdapter(private val onItemClick: (myOrder: Order) -> Unit) :
    RecyclerView.Adapter<MyOrdersAdapter.ViewHolder>() {

    private val myOrderList = mutableListOf<Order>()

    fun addItems(myOrderList: List<Order>) {
        this.myOrderList.clear()
        this.myOrderList.addAll(myOrderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemMyOrderBinding =
            ItemMyOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMyOrderBinding)
    }

    override fun getItemCount(): Int = myOrderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myOrder = myOrderList[position]
        holder.bind(myOrder)
        holder.itemView.setOnClickListener { onItemClick(myOrder) }
    }

    inner class ViewHolder(private val binding: ItemMyOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myOrder: Order) {
            with(binding) {
                tvEstimate.text = myOrder.estimateArrival
                tvOrderId.text = myOrder.trackId
                tvDate.text = DateUtils.toFormatDate(myOrder.createDtm!!)

                when (myOrder.flag) {
                    Flag.FROM -> {
                        tvWarehouseName.text = "From ${myOrder.orderFrom}"
                        icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_order_from))
                    }
                    Flag.TO -> {
                        tvWarehouseName.text = "To ${myOrder.orderTo}"
                        icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_order_to))
                    }
                }
            }
        }
    }

}