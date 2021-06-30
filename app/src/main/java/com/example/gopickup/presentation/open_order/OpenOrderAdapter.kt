package com.example.gopickup.presentation.open_order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.databinding.ItemOpenOrderBinding
import com.example.gopickup.model.dummy.MyOrder

class OpenOrderAdapter(private val onItemClick: (myOrder: MyOrder) -> Unit) :
    RecyclerView.Adapter<OpenOrderAdapter.ViewHolder>() {

    private val myOrderList = mutableListOf<MyOrder>()

    fun addItems(myOrderList: List<MyOrder>) {
        this.myOrderList.clear()
        this.myOrderList.addAll(myOrderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemOpenOrderBinding =
            ItemOpenOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemOpenOrderBinding)
    }

    override fun getItemCount(): Int = myOrderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myOrder = myOrderList[position]
        holder.bind(myOrder)
        holder.itemView.setOnClickListener { onItemClick(myOrder) }
    }

    inner class ViewHolder(private val binding: ItemOpenOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myOrder: MyOrder) {
            with(binding) {
                tvWarehouseName.text = myOrder.warehouseName
                tvEstimate.text = myOrder.estimate
                tvOrderId.text = "Order ID #${myOrder.orderId}"
                tvDate.text = myOrder.date
            }
        }
    }

}