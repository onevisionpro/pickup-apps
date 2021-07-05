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

    override fun postEditProfile(profile: BaseRequest<Profile>): Observable<BaseResponse<Any>> {
        return apiRest.postEditProfile(profile)
    }

    override fun getHistoryOrderList(historyOrderRequest: BaseRequest<TrackId>): Observable<BaseResponse<List<HistoryOrder>>> {
        return apiRest.getHistoryOrderList(historyOrderRequest)
    }

    override fun getHistoryOrderDetails(historyOrderRequest: BaseRequest<TrackId>): Observable<BaseResponse<List<HistoryOrderDetails>>> {
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

}