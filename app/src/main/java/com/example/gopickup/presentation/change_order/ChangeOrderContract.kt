package com.example.gopickup.presentation.change_order

import com.example.gopickup.base.BasePresenter
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseView
import com.example.gopickup.model.SelectedItem
import com.example.gopickup.model.request.EditOrder
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.ItemWarehouse
import com.example.gopickup.model.response.OrderDetails
import com.example.gopickup.model.response.Warehouse

interface ChangeOrderContract {

    interface View : BaseView {
        fun showOrderDetails(orderDetails: OrderDetails)
        fun showWarehouseList(warehouseList: List<Warehouse>?)
        fun showItemList(itemList: List<ItemWarehouse>?)
        fun showSelectedItemList(selectedItemList: List<SelectedItem>?)
        fun showEditOrderSuccess(orderId: String)
        fun showCancelOrderSuccess(message: String)
    }

    interface Presenter : BasePresenter {
        fun getOrderDetails(trackId: BaseRequest<TrackId>)
        fun getWarehouseList(baseRequest: BaseRequest<String>)
        fun getItemList(baseRequest: BaseRequest<String>)
        fun getSelectedItems(selectedItemList: List<SelectedItem>)
        fun postEditOrder(editOrder: BaseRequest<EditOrder>)
        fun postCancelOrder(trackId: BaseRequest<TrackId>)
    }
}