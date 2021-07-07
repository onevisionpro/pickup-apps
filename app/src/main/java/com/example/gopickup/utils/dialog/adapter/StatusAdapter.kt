package com.example.gopickup.utils.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.databinding.ItemStatusBinding

class StatusAdapter(private val onItemClick: (item: String) -> Unit) :
    RecyclerView.Adapter<StatusAdapter.ViewHolder>() {

    private val statusList = mutableListOf<String>()

    fun addItems(statusList: List<String>) {
        this.statusList.clear()
        this.statusList.addAll(statusList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemStatusBinding = ItemStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemStatusBinding)
    }

    override fun getItemCount(): Int = statusList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = statusList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    inner class ViewHolder(private val binding: ItemStatusBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(status: String) {
            with(binding) {
                tvStatus.text = status
            }
        }
    }

}