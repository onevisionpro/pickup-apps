package com.example.gopickup.presentation.create_order

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.SelectedItem
import com.example.gopickup.model.request.CreateOrder
import com.example.gopickup.model.response.ItemWarehouse
import com.example.gopickup.model.response.Warehouse

interface CreateOrderContract {

    interface View : BaseView {
        fun showWarehouseList(warehouseList: List<Warehouse>?)
        fun showItemList(itemList: List<ItemWarehouse>?)
        fun showCreateOrderSuccess(trackId: String)
        fun showSelectedItemList(selectedItemList: List<SelectedItem>?)
    }

    interface Presenter : BasePresenter {
        fun getWarehouseList(baseRequest: BaseRequest<String>)
        fun getItemList(baseRequest: BaseRequest<String>)
        fun postCreateOrder(createOrder: BaseRequest<CreateOrder>)
        fun addToSelectedItems(selectedItemList: List<SelectedItem>)
    }
}