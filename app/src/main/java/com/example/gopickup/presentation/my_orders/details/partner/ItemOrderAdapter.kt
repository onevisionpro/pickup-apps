package com.example.gopickup.presentation.my_orders.details.partner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.databinding.ItemItemOrderBinding
import com.example.gopickup.model.response.ItemOrder

class ItemOrderAdapter : RecyclerView.Adapter<ItemOrderAdapter.ViewHolder>() {

    private val itemOrderList = mutableListOf<ItemOrder>()

    fun addItems(itemOrderList: List<ItemOrder>) {
        this.itemOrderList.clear()
        this.itemOrderList.addAll(itemOrderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemItemOrderBinding =
            ItemItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemItemOrderBinding)
    }

    override fun getItemCount(): Int = itemOrderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemOrder = itemOrderList[position]
        holder.bind(itemOrder)
        holder.binding.apply {
            tvItemCount.text = "Item ${position.plus(1)}"
        }
    }

    inner class ViewHolder(val binding: ItemItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemOrder: ItemOrder) {
            with(binding) {
                tvItemName.text = itemOrder.itemName
            }
        }
    }
}
