package com.example.gopickup.presentation.change_order

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityChangeOrderBinding
import com.example.gopickup.model.SelectedItem
import com.example.gopickup.model.request.EditOrder
import com.example.gopickup.model.request.Item
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.ItemWarehouse
import com.example.gopickup.model.response.OrderDetails
import com.example.gopickup.model.response.Warehouse
import com.example.gopickup.utils.*
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogCancelOrderListener
import com.example.gopickup.utils.dialog.listener.IOnDialogChangeMyOrderListener
import com.example.gopickup.utils.dialog.listener.IOnItemClicked
import java.util.*

class ChangeOrderActivity : BaseActivity(), ChangeOrderContract.View {

    companion object {
        const val TRACK_ID = "TRACK_ID"
    }

    private var _binding: ActivityChangeOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ChangeOrderPresenter
    private var editOrder = EditOrder()
    private var item = Item()
    private var items = arrayListOf<Item>()
    private var selectedItems = arrayListOf<SelectedItem>()

    private val selectedItemsAdapter = SelectedItemsAdapter(object : IOnButtonCounter {
        override fun onMinusClicked(selectedItem: SelectedItem, position: Int) {
            if (selectedItem.quantity == "1") {
                selectedItems.remove(selectedItem)

                item = Item(idItem = selectedItem.itemId, jumlah = selectedItem.quantity)
                items.remove(item)

                updateSelectedItem(selectedItem.itemId, selectedItem.quantity)
            } else {
                updateSelectedItem(selectedItem.itemId, selectedItem.quantity)
            }
        }

        override fun onPlusClicked(selectedItem: SelectedItem) {
            updateSelectedItem(selectedItem.itemId, selectedItem.quantity)
        }

    })

    private fun updateSelectedItem(id: String, qty: String) {
        selectedItems.filter { item -> item.itemId == id }
            .forEach { it.quantity = qty }
        items.filter { item -> item.idItem == id }
            .forEach { it.jumlah = qty }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChangeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ChangeOrderPresenter(this, callApi())
        presenter.start()
        presenter.getOrderDetails(trackId = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = TrackId(trackId = intent.getStringExtra(TRACK_ID))
        ))
        presenter.getWarehouseList(
            baseRequest = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = ""
            )
        )
        presenter.getItemList(
            baseRequest = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = ""
            )
        )
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "Ubah Pesanan Saya"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnCancelOrder.setOnClickListener {
            presenter.postCancelOrder(trackId = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = TrackId(trackId = intent.getStringExtra(TRACK_ID))
            ))
        }
    }

    override fun showOrderDetails(orderDetails: OrderDetails) {
        binding.edtChooseWarehouse.setText(orderDetails.orderTo)
        binding.edtEstimatedDate.setText(orderDetails.arrivalEstimate)
        binding.edtEstimatedDate.setOnClickListener {
            DialogUtils.showDialogCalendar(this, object : IOnItemClicked<Date> {
                override fun onItemClicked(data: Date) {
                    binding.edtEstimatedDate.setText(DateUtils.formatDate(data))
                    editOrder.arrivalDate = binding.edtEstimatedDate.text.toString()
                }

            })
        }

        for (item in orderDetails.items?.iterator()!!) {
            selectedItems.add(SelectedItem(
                itemId = item.idItem!!,
                itemName = item.itemName!!,
                quantity = item.jumlah!!
            ))
        }
        presenter.getSelectedItems(selectedItemList = selectedItems)

        // add item
        binding.btnAddItem.setOnClickListener {
            val itemName = binding.edtChooseItem.text.toString()

            if (itemName.isNotEmpty()) {
                val isAlreadyHave = selectedItems.filter { it.itemName == itemName }.size == 1
                if (isAlreadyHave) {
                    showToast("Anda telah memilih Item $itemName")
                } else {

                    val selectedItem = SelectedItem(
                        itemId = item.idItem!!,
                        itemName = itemName,
                        quantity = "1"
                    )
                    items.add(this.item)
                    editOrder.items = items

                    selectedItemsAdapter.addAnotherItem(0, selectedItem)
                    selectedItems.add(selectedItem)
                    presenter.getSelectedItems(selectedItems)
                }
//                val selectedItem = SelectedItem(
//                    itemId = item.idItem!!,
//                    itemName = itemName,
//                    quantity = "1"
//                )
//                items.add(this.item)
//                editOrder.items = items
//
//                selectedItemsAdapter.addAnotherItem(0, selectedItem)
//                selectedItems.add(selectedItem)
//                presenter.getSelectedItems(selectedItems)
            } else {
                showToast("Pilih item terlebih dahulu")
            }
        }

        binding.btnUpdateOrder.setOnClickListener {
            editOrder.track_id = intent.getStringExtra(TRACK_ID)
            editOrder.arrivalDate = binding.edtEstimatedDate.text.toString()
            editOrder.idWarehouse = orderDetails.idWarehouseTo
            editOrder.items = items

            presenter.postEditOrder(editOrder = BaseRequest(
                guid = provideGUID(),
                code = "",
                data = editOrder
            ))
        }
    }

    override fun showWarehouseList(warehouseList: List<Warehouse>?) {
        warehouseList?.let {
            binding.edtChooseWarehouse.setOnClickListener {
                DialogUtils.showDialogWarehouse(this, warehouseList,
                    object : IOnItemClicked<Warehouse> {
                        override fun onItemClicked(data: Warehouse) {
                            binding.edtChooseWarehouse.setText(data.whName)

                            editOrder.idWarehouse = data.idWarehouse
                        }

                    })
            }
        }
    }

    override fun showItemList(itemList: List<ItemWarehouse>?) {
        itemList?.let {
            binding.edtChooseItem.setOnClickListener {
                DialogUtils.showDialogItems(this, itemList, object : IOnItemClicked<ItemWarehouse> {
                    override fun onItemClicked(data: ItemWarehouse) {
                        binding.edtChooseItem.setText(data.itemName)
                        item = Item(idItem = data.idItem, jumlah = "1")
                    }

                })
            }
        }
    }

    override fun showSelectedItemList(selectedItemList: List<SelectedItem>?) {
        selectedItemList?.let {
            if (it.isNotEmpty()) {
                binding.layoutSelectedItems.show()

                selectedItemsAdapter.addItems(it)
                binding.rvSelectedItems.apply {
                    layoutManager = LinearLayoutManager(
                        this@ChangeOrderActivity,
                        RecyclerView.VERTICAL,
                        false
                    )
                    adapter = selectedItemsAdapter
                }

                val items = arrayListOf<Item>()
                for (item in it.listIterator()) {
                    val item = Item(idItem = item.itemId, jumlah = item.quantity)
                    items.add(item)
                }
                this.items = items
            } else {
                binding.layoutSelectedItems.hide()
                binding.rvSelectedItems.hide()
            }
        }
    }

    override fun showEditOrderSuccess(orderId: String) {
        DialogUtils.showDialogChangeMyOrder(this, orderId,
            object : IOnDialogChangeMyOrderListener {
            override fun onTrackMyOrderClicked() {
                NavigationUtils.navigateToTrackMyOrderActivity(this@ChangeOrderActivity)
                finish()
            }

            override fun onBackToHomeClicked() {
                NavigationUtils.navigateToMainActivity(this@ChangeOrderActivity)
                finish()
            }

        })
    }

    override fun showCancelOrderSuccess(message: String) {
        val orderId = intent.getStringExtra(TRACK_ID)!!
        DialogUtils.showDialogCancelOrder(this, orderId, object : IOnDialogCancelOrderListener {
            override fun onBackToHomeClicked() {
                NavigationUtils.navigateToMainActivity(this@ChangeOrderActivity)
                finish()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}