package com.example.gopickup.utils.dialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.databinding.ItemCreateOrderBinding
import com.example.gopickup.model.response.ItemWarehouse

class ItemAdapter(private val onItemClick: (item: ItemWarehouse) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val itemList = mutableListOf<ItemWarehouse>()

    fun addItems(itemList: List<ItemWarehouse>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemCreateOrderBinding = ItemCreateOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemCreateOrderBinding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    inner class ViewHolder(private val binding: ItemCreateOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemWarehouse) {
            with(binding) {
                tvItemName.text = item.itemName
            }
        }
    }

}