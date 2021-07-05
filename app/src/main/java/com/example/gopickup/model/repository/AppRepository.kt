package com.example.gopickup.model.repository

import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.*
import com.example.gopickup.model.response.*
import io.reactivex.Observable

interface AppRepository {

    fun postVersionChecker(baseRequest: BaseRequest<String>): Observable<BaseResponse<VersionChecker>>

    fun postLoginAndOTP(login: Login): Observable<BaseResponse<User>>

    fun postOTP(login: Login): Observable<BaseResponse<User>>

    fun postResendOTP(resendOTPRequest: BaseRequest<ResendOTPRequest>): Observable<BaseResponse<Any>>

    fun getHomeInformation(baseRequest: BaseRequest<String>): Observable<BaseResponse<HomeInformation>>

    fun getRecentOrderItems(recentOrder: BaseRequest<RecentOrder>): Observable<BaseResponse<List<RecentOrderItem>>>

    fun getProfile(profileRequest: BaseRequest<String>): Observable<BaseResponse<Profile>>

    fun postEditProfile(profile: BaseRequest<Profile>): Observable<BaseResponse<Any>>

    fun postResetPassword(resetPassword: BaseRequest<ResetPassword>): Observable<BaseResponse<Any>>

    fun getHistoryOrderList(historyOrderRequest: BaseRequest<TrackId>): Observable<BaseResponse<List<HistoryOrder>>>

    fun getHistoryOrderDetails(historyOrderRequest: BaseRequest<TrackId>): Observable<BaseResponse<List<HistoryOrderDetails>>>

    fun getWarehouseList(baseRequest: BaseRequest<String>): Observable<BaseResponse<List<Warehouse>>>

    fun getItemListWarehouse(baseRequest: BaseRequest<String>): Observable<BaseResponse<List<ItemWarehouse>>>

    fun postCreateOrder(createOrder: BaseRequest<CreateOrder>): Observable<BaseResponse<TrackId>>
}