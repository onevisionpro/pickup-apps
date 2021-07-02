package com.example.gopickup.model.repository

import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.request.RecentOrder
import com.example.gopickup.model.request.ResendOTPRequest
import com.example.gopickup.model.response.HomeInformation
import com.example.gopickup.model.response.RecentOrderItem
import com.example.gopickup.model.response.User
import com.example.gopickup.model.response.VersionChecker
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

}