package com.example.gopickup.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gopickup.databinding.ItemItemsHomeBinding
import com.example.gopickup.model.response.Item

class ItemsAdapter(private val onItemClick: (item: Item) -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val itemList = mutableListOf<Item>()

    fun addItems(itemList: List<Item>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsHomeBinding =
            ItemItemsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsHomeBinding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemItemsHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(imgItem)
                tvNameItem.text = item.label
            }
        }
    }

}