package com.example.gopickup.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.databinding.ItemHistoryBinding
import com.example.gopickup.model.dummy.History

class HistoryAdapter(private val onItemClick: (history: History) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val historyList = mutableListOf<History>()

    fun addItems(historyList: List<History>) {
        this.historyList.clear()
        this.historyList.addAll(historyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHistoryBinding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemHistoryBinding)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history)
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            with(binding) {
                tvWarehouseName.text = history.warehouseName
                tvOrderId.text = history.orderId
                tvStatus.text = history.statusDesc
                tvDate.text = history.date

                when (history.status) {
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