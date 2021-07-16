package com.example.gopickup.utils.dialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.databinding.ItemWarehouseBinding
import com.example.gopickup.model.response.Warehouse
import java.util.*
import kotlin.collections.ArrayList

class WarehouseAdapter(private val onItemClick: (warehouse: Warehouse) -> Unit) :
    RecyclerView.Adapter<WarehouseAdapter.ViewHolder>(), Filterable {

    private val warehouseList = mutableListOf<Warehouse>()
    private var warehouseFilterList = mutableListOf<Warehouse>()

    init {
        warehouseFilterList = warehouseList
    }

    fun addItems(warehouseList: List<Warehouse>) {
        this.warehouseList.clear()
        this.warehouseList.addAll(warehouseList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemWarehouseBinding = ItemWarehouseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemWarehouseBinding)
    }

    override fun getItemCount(): Int = warehouseFilterList.size

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) warehouseFilterList = warehouseList else {
                    val filteredList = ArrayList<Warehouse>()
                    warehouseList
                        .filter {
                            (it.whName!!.contains(constraint!!))
                        }
                        .forEach { filteredList.add(it) }
                    warehouseFilterList = filteredList

                }
                return FilterResults().apply { values = warehouseFilterList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                warehouseFilterList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Warehouse>
                notifyDataSetChanged()
            }

        }
    }

}