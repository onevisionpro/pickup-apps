package com.example.gopickup.presentation.create_order

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.databinding.ItemSelectedItemBinding
import com.example.gopickup.model.SelectedItem
import com.example.gopickup.model.request.Item

class SelectedItemsAdapter(private val listener: IOnButtonCounter) :
    RecyclerView.Adapter<SelectedItemsAdapter.ViewHolder>() {

    private val itemList = mutableListOf<SelectedItem>()

    fun addItems(itemList: List<SelectedItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemSelectedItemBinding =
            ItemSelectedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemSelectedItemBinding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.binding.apply {
            holder.binding.edtQtyCounter.setOnClickListener {
                holder.binding.edtQtyCounter.isEnabled = true
            }
            var qty = holder.binding.edtQtyCounter.text.toString().toInt()
            btnQtyPlus.setOnClickListener {
                qty++
                item.quantity = qty.toString()

//                tvQtyCounter.text = qty.toString()
                edtQtyCounter.setText(qty.toString())
                listener.onPlusClicked(item)
            }
            btnQtyMinus.setOnClickListener {
                if (qty != 0) {
                    if (qty == 1) {
                        itemList.remove(item)
                        notifyItemChanged(position)
                        notifyItemRangeRemoved(position, itemList.size)
                        listener.onMinusClicked(item, position)
                    } else {
                        qty--
                        item.quantity = qty.toString()

//                    tvQtyCounter.text = qty.toString()
                        edtQtyCounter.setText(qty.toString())

                        listener.onMinusClicked(item, position)

                    }
                } else {
                    itemList.remove(item)
                    notifyItemChanged(position)
                    notifyItemRangeRemoved(position, itemList.size)
                }

            }

            edtQtyCounter.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString() != "") {
                        qty = s.toString().toInt()

                        item.quantity = qty.toString()
                        listener.onTextChanged(item)

                        if (s.toString() == "0") {
                            itemList.remove(item)
                            notifyItemChanged(position)
                            notifyItemRangeRemoved(position, itemList.size)
                        }
                    }
                }

            })
        }

    }

    inner class ViewHolder(val binding: ItemSelectedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SelectedItem) {
            with(binding) {
                tvItemName.text = item.itemName
                tvQtyCounter.text = item.quantity
                edtQtyCounter.setText(item.quantity)

                btnQtyPlus.setOnClickListener {
                    edtQtyCounter.isEnabled = true
                    edtQtyCounter.inputType = InputType.TYPE_CLASS_NUMBER
                }
            }
        }
    }
}

interface IOnButtonCounter {
    fun onMinusClicked(selectedItem: SelectedItem, position: Int)
    fun onPlusClicked(selectedItem: SelectedItem)
    fun onTextChanged(selectedItem: SelectedItem)
}