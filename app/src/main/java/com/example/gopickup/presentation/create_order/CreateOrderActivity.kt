package com.example.gopickup.presentation.create_order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityCreateOrderBinding
import com.example.gopickup.model.SelectedItem
import com.example.gopickup.model.request.CreateOrder
import com.example.gopickup.model.request.Item
import com.example.gopickup.model.response.ItemWarehouse
import com.example.gopickup.model.response.Warehouse
import com.example.gopickup.presentation.main.MainActivity
import com.example.gopickup.utils.*
import com.example.gopickup.utils.dialog.DialogUtils
import com.example.gopickup.utils.dialog.listener.IOnDialogCreateOrderListener
import com.example.gopickup.utils.dialog.listener.IOnItemClicked
import java.util.*

class CreateOrderActivity : BaseActivity(), CreateOrderContract.View {

    private var _binding: ActivityCreateOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: CreateOrderPresenter
    private var createOrder = CreateOrder()
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

            Log.d("TAG", "onMinusClicked: selectedItemSize: ${selectedItems.size}")
        }

        override fun onPlusClicked(selectedItem: SelectedItem) {
            updateSelectedItem(selectedItem.itemId, selectedItem.quantity)
        }

        override fun onTextChanged(selectedItem: SelectedItem) {
            if (selectedItem.quantity == "0") {
                selectedItems.remove(selectedItem)

                item = Item(idItem = selectedItem.itemId, jumlah = selectedItem.quantity)
                items.remove(item)

                updateSelectedItem(selectedItem.itemId, selectedItem.quantity)
            } else {
                updateSelectedItem(selectedItem.itemId, selectedItem.quantity)
            }
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
        _binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CreateOrderPresenter(this, callApi())
        presenter.start()
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
        binding.layoutParent.setOnClickListener { hideKeyboard() }
        binding.toolbar.tvToolbarTitle.text = "Buat Pesanan"
        binding.toolbar.icBack.setOnClickListener { finish() }

        // button estimated date
        val currentDate = DateUtils.formatCurrentDate(Calendar.getInstance().time)
        binding.edtEstimatedDate.setText(currentDate)
        createOrder.arrivalDate = binding.edtEstimatedDate.text.toString()

        binding.edtEstimatedDate.setOnClickListener {
            DialogUtils.showDialogCalendar(this, object : IOnItemClicked<Date> {
                override fun onItemClicked(data: Date) {
                    binding.edtEstimatedDate.setText(DateUtils.formatDate(data))
                    createOrder.arrivalDate = binding.edtEstimatedDate.text.toString()
                }

            })
        }

        // button add items
        binding.btnAddItem.setOnClickListener {
            val warehouse = binding.edtChooseWarehouse.text.toString()
            val itemName = binding.edtChooseItem.text.toString()
            val qty = binding.tvQtyCounter.text.toString()

            if (warehouse.isNotEmpty() && itemName.isNotEmpty()) {
                val isAlreadyHave = selectedItems.filter { it.itemName == itemName }.size == 1
                if (isAlreadyHave) {
                    showToast("Anda telah memilih Item $itemName")
                } else {

                    items.add(this.item)
                    createOrder.items = items

                    val selectedItem = SelectedItem(
                        itemId = item.idItem!!,
                        itemName = itemName,
                        quantity = qty
                    )
                    selectedItems.add(selectedItem)
                    presenter.addToSelectedItems(selectedItems)
                }


            } else {
                showToast("Pilih warehouse dan item terlebih dahulu!")
            }
        }

        binding.btnOrder.setOnClickListener {
            val warehouseName = binding.edtChooseWarehouse.text.toString()
            val selectedItems = selectedItems.size
            val estimateDate = binding.edtEstimatedDate.text.toString()
            when {
                warehouseName.isEmpty() -> showToast("Pilih Warehouse Tujuan terlebih dahulu")
                selectedItems == 0 -> showToast("Pilih item terlebih dahulu ")
                estimateDate.isEmpty() -> showToast("Pilih Estimasi terlebih dahulu")
                else -> {
                    presenter.postCreateOrder(
                        createOrder = BaseRequest(
                            guid = provideGUID(),
                            code = "",
                            data = createOrder
                        )
                    )
                }
            }
        }
    }

    override fun showWarehouseList(warehouseList: List<Warehouse>?) {
        warehouseList?.let {
            binding.edtChooseWarehouse.setOnClickListener {
                DialogUtils.showDialogWarehouse(this, warehouseList,
                    object : IOnItemClicked<Warehouse> {
                        override fun onItemClicked(data: Warehouse) {
                            binding.edtChooseWarehouse.setText(data.whName)

                            createOrder.idWarehouse = data.idWarehouse
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

    override fun showCreateOrderSuccess(trackId: String) {
        DialogUtils.showDialogCreateOrder(this, trackId, object : IOnDialogCreateOrderListener {
            override fun onHistoryOrderClicked() {
                val intent = Intent(this@CreateOrderActivity, MainActivity::class.java)
                intent.putExtra("NAVIGATE_TO", "HISTORY")
                startActivity(intent)
                finish()
            }

            override fun onBackToHomeClicked() {
                NavigationUtils.navigateToMainActivity(this@CreateOrderActivity)
                finish()
            }

        })
    }

    override fun showSelectedItemList(selectedItemList: List<SelectedItem>?) {
        selectedItemList?.let {
            if (it.isNotEmpty()) {
                binding.layoutSelectedItems.show()

                selectedItemsAdapter.addItems(it)
                binding.rvSelectedItems.apply {
                    layoutManager = LinearLayoutManagerWrapper(
                        this@CreateOrderActivity,
                        RecyclerView.VERTICAL,
                        false
                    )
                    adapter = selectedItemsAdapter
                }
            } else {
                binding.layoutSelectedItems.hide()
                binding.rvSelectedItems.hide()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}