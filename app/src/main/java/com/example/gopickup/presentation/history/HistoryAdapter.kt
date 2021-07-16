package com.example.gopickup.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.R
import com.example.gopickup.databinding.ItemHistoryBinding
import com.example.gopickup.model.dummy.History
import com.example.gopickup.model.response.HistoryOrder
import com.example.gopickup.utils.DateUtils
import com.example.gopickup.utils.OrderStatus

class HistoryAdapter(private val onItemClick: (history: HistoryOrder) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val historyList = mutableListOf<HistoryOrder>()

    fun addItems(historyList: List<HistoryOrder>) {
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
        holder.itemView.setOnClickListener { onItemClick(history) }
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: HistoryOrder) {
            with(binding) {
                tvWarehouseName.text = history.orderTo
                tvOrderId.text = history.trackId
                tvStatus.text = history.status
                tvDate.text = DateUtils.toFormatDate(history.createDtm!!)

                when (history.status) {
                    OrderStatus.FINISH -> {
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