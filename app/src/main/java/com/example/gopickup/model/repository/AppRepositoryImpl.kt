package com.example.gopickup.model.repository

import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.*
import com.example.gopickup.model.response.*
import com.example.gopickup.network.ApiRest
import io.reactivex.Observable

class AppRepositoryImpl(private val apiRest: ApiRest) : AppRepository {

    override fun postVersionChecker(baseRequest: BaseRequest<String>): Observable<BaseResponse<VersionChecker>> {
        return apiRest.postVersionChecker(baseRequest)
    }

    override fun postLoginAndOTP(login: Login): Observable<BaseResponse<User>> {
        return apiRest.postLogin(login)
    }

    override fun postLogout(logout: BaseRequest<Logout>): Observable<BaseResponse<Any>> {
        return apiRest.postLogout(logout)
    }

    override fun postOTP(login: Login): Observable<BaseResponse<User>> {
        return apiRest.postOTP(login)
    }

    override fun postResendOTP(resendOTPRequest: BaseRequest<ResendOTPRequest>): Observable<BaseResponse<Any>> {
        return apiRest.postResendOTP(resendOTPRequest)
    }

    override fun getHomeInformation(baseRequest: BaseRequest<String>): Observable<BaseResponse<HomeInformation>> {
        return apiRest.getHomeInformation(baseRequest)
    }

    override fun getRecentOrderItems(recentOrder: BaseRequest<RecentOrder>): Observable<BaseResponse<List<RecentOrderItem>>> {
        return apiRest.getRecentOrderItems(recentOrder)
    }

    override fun getProfile(profileRequest: BaseRequest<String>): Observable<BaseResponse<Profile>> {
        return apiRest.getProfile(profileRequest)
    }

    override fun postEditProfile(profile: BaseRequest<EditProfile>): Observable<BaseResponse<Any>> {
        return apiRest.postEditProfile(profile)
    }

    override fun postResetPassword(resetPassword: BaseRequest<ResetPassword>): Observable<BaseResponse<Any>> {
        return apiRest.postResetPassword(resetPassword)
    }

    override fun getHistoryOrderList(historyOrderRequest: BaseRequest<HistoryOrderRequest>): Observable<BaseResponse<List<HistoryOrder>>> {
        return apiRest.getHistoryOrderList(historyOrderRequest)
    }

    override fun getHistoryOrderDetails(historyOrderRequest: BaseRequest<TrackId>): Observable<BaseResponse<List<OrderDetails>>> {
        return apiRest.getHistoryOrderDetails(historyOrderRequest)
    }

    override fun getWarehouseList(baseRequest: BaseRequest<String>): Observable<BaseResponse<List<Warehouse>>> {
        return apiRest.getWarehouses(baseRequest)
    }

    override fun getItemListWarehouse(baseRequest: BaseRequest<String>): Observable<BaseResponse<List<ItemWarehouse>>> {
        return apiRest.getItemsWarehouse(baseRequest)
    }

    override fun postCreateOrder(createOrder: BaseRequest<CreateOrder>): Observable<BaseResponse<TrackId>> {
        return apiRest.postCreateOrder(createOrder)
    }

    override fun getWordings(moreType: BaseRequest<Type>): Observable<BaseResponse<List<AboutApps>>> {
        return apiRest.getWordings(moreType)
    }

    override fun getMyOrderList(trackId: BaseRequest<TrackId>): Observable<BaseResponse<List<Order>>> {
        return apiRest.getMyOrderList(trackId)
    }

    override fun getOrderDetails(trackId: BaseRequest<TrackId>): Observable<BaseResponse<List<OrderDetails>>> {
        return apiRest.getMyOrderDetails(trackId)
    }

    override fun postEditOrder(editOrder: BaseRequest<EditOrder>): Observable<BaseResponse<OrderId>> {
        return apiRest.postEditOrder(editOrder)
    }

    override fun postCancelOrder(trackId: BaseRequest<TrackId>): Observable<BaseResponse<Any>> {
        return apiRest.postCancelOrder(trackId)
    }

    override fun getOpenOrderList(trackId: BaseRequest<TrackId>): Observable<BaseResponse<List<Order>>> {
        return apiRest.getOpenOrderList(trackId)
    }

    override fun getOpenOrderDetails(trackId: BaseRequest<TrackId>): Observable<BaseResponse<List<OrderDetails>>> {
        return apiRest.getOpenOrderDetails(trackId)
    }

    override fun postBookOrder(trackId: BaseRequest<TrackId>): Observable<BaseResponse<Any>> {
        return apiRest.postBookOrder(trackId)
    }

    override fun postTakeOrder(takeOrder: BaseRequest<TakeOrder>): Observable<BaseResponse<Any>> {
        return apiRest.postTakeOrder(takeOrder)
    }

    override fun getPreviewBA(trackId: BaseRequest<PreviewBARequest>): Observable<BaseResponse<PreviewBA>> {
        return apiRest.getPreviewBA(trackId)
    }

    override fun postSendOrder(sendOrder: BaseRequest<SendOrder>): Observable<BaseResponse<Any>> {
        return apiRest.postSendOrder(sendOrder)
    }

    override fun postOrderArrived(trackId: BaseRequest<TrackId>): Observable<BaseResponse<Any>> {
        return apiRest.postOrderArrived(trackId)
    }

    override fun postReceivedOrder(receiveOrderRequest: BaseRequest<ReceiveOrderRequest>): Observable<BaseResponse<Any>> {
        return apiRest.postReceivedOrder(receiveOrderRequest)
    }

    override fun postFinishOrder(finishOrder: BaseRequest<FinishOrder>): Observable<BaseResponse<Any>> {
        return apiRest.postFinishOrder(finishOrder)
    }

    override fun getStatusList(baseRequest: BaseRequest<String>): Observable<BaseResponse<List<String>>> {
        return apiRest.getStatus(baseRequest)
    }

    override fun postEditPhotoProfile(newImage: BaseRequest<NewImage>): Observable<BaseResponse<Any>> {
        return apiRest.postEditPhotoProfile(newImage)
    }

    override fun postGenerateBA(trackId: BaseRequest<TrackId>): Observable<BaseResponse<BA>> {
        return apiRest.postGenerateBA(trackId)
    }

    override fun getOrderCount(baseRequest: BaseRequest<String>): Observable<BaseResponse<OrderCount>> {
        return apiRest.postOrderCount(baseRequest)
    }


}