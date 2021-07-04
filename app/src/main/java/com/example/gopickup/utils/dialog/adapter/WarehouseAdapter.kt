package com.example.gopickup.utils.dialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.databinding.ItemWarehouseBinding
import com.example.gopickup.model.response.Warehouse

class WarehouseAdapter(private val onItemClick: (warehouse: Warehouse) -> Unit) :
    RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {

    private val warehouseList = mutableListOf<Warehouse>()

    fun addItems(warehouseList: List<Warehouse>) {
        this.warehouseList.clear()
        this.warehouseList.addAll(warehouseList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemWarehouseBinding = ItemWarehouseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemWarehouseBinding)
    }

    override fun getItemCount(): Int = warehouseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val warehouse = warehouseList[position]
        holder.bind(warehouse)
        holder.itemView.setOnClickListener { onItemClick(warehouse) }
    }

    inner class ViewHolder(private val binding: ItemWarehouseBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(warehouse: Warehouse) {
            with(binding) {
                tvWarehouseName.text = warehouse.whName
            }
        }
    }

}