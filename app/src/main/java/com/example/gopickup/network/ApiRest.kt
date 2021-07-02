package com.example.gopickup.network

import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.request.RecentOrder
import com.example.gopickup.model.request.ResendOTPRequest
import com.example.gopickup.model.request.TrackId
import com.example.gopickup.model.response.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRest {

    @POST("login")
    fun postLogin(@Body login: Login): Observable<BaseResponse<User>>

    @POST("login")
    fun postOTP(@Body login: Login): Observable<BaseResponse<User>>

    @POST("login/resendOTP")
    fun postResendOTP(@Body baseRequest: BaseRequest<ResendOTPRequest>): Observable<BaseResponse<Any>>

    @POST("version/checker")
    fun postVersionChecker(@Body baseRequest: BaseRequest<String>): Observable<BaseResponse<VersionChecker>>

    /**
     * Home Page
     */
    @POST("home/information")
    fun getHomeInformation(@Body baseRequest: BaseRequest<String>): Observable<BaseResponse<HomeInformation>>

    @POST("home/recentOrder")
    fun getRecentOrderItems(@Body recentOrderRequest: BaseRequest<RecentOrder>): Observable<BaseResponse<List<RecentOrderItem>>>

    /**
     * Profile
     */
    @POST("profile/information")
    fun getProfile(@Body profileRequest: BaseRequest<String>): Observable<BaseResponse<Profile>>

    /**
     * History Order
     */
    @POST("order/historyOrder")
    fun getHistoryOrderList(@Body historyOrderRequest: BaseRequest<TrackId>): Observable<BaseResponse<List<HistoryOrder>>>
}