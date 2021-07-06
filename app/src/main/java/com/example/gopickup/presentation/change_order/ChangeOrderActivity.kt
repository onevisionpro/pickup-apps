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
import com.example.gopickup.utils.NavigationUtils
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogCancelOrderListener
import com.example.gopickup.utils.dialog.listener.IOnDialogChangeMyOrderListener
import com.example.gopickup.utils.dialog.listener.IOnItemClicked
import com.example.gopickup.utils.hide
import com.example.gopickup.utils.show
import com.example.gopickup.utils.showToast

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
        binding.toolbar.tvToolbarTitle.text = "Change My Order"
        binding.toolbar.icBack.setOnClickListener { finish() }

        binding.btnAddItem.setOnClickListener {
            val itemName = binding.edtChooseItem.text.toString()

            items.add(this.item)
            editOrder.items = items

            if (itemName.isNotEmpty()) {
                val selectedItem = SelectedItem(
                    itemId = item.idItem!!,
                    itemName = itemName,
                    quantity = "1"
                )
                selectedItemsAdapter.addAnotherItem(0, selectedItem)
                selectedItems.add(selectedItem)
                presenter.getSelectedItems(selectedItems)
            } else {
                showToast("Pilih item terlebih dahulu")
            }
        }

        binding.btnCancelOrder.setOnClickListener {
            DialogUtils.showDialogCancelOrder(this, object : IOnDialogCancelOrderListener {
                override fun onBackToHomeClicked() {
                    NavigationUtils.navigateToMainActivity(this@ChangeOrderActivity)
                    finish()
                }

            })
        }
    }

    override fun showOrderDetails(orderDetails: OrderDetails) {
        binding.edtChooseWarehouse.setText(orderDetails.orderTo)
        binding.edtEstimatedDate.setText(orderDetails.arrivalEstimate)

        for (item in orderDetails.items?.iterator()!!) {
            selectedItems.add(SelectedItem(
                itemId = item.idItem!!,
                itemName = item.itemName!!,
                quantity = item.jumlah!!
            ))
        }
        Log.d("TAG", "selectedItems: $selectedItems")
        presenter.getSelectedItems(selectedItemList = selectedItems)

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}