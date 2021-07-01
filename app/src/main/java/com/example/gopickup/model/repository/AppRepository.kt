package com.example.gopickup.model.repository

import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.request.ResendOTPRequest
import com.example.gopickup.model.response.User
import com.example.gopickup.model.response.VersionChecker
import io.reactivex.Observable

interface AppRepository {

    fun postVersionChecker(baseRequest: BaseRequest<String>): Observable<BaseResponse<VersionChecker>>

    fun postLoginAndOTP(login: Login): Observable<BaseResponse<User>>

    fun postOTP(login: Login): Observable<BaseResponse<User>>

    fun postResendOTP(resendOTPRequest: BaseRequest<ResendOTPRequest>): Observable<BaseResponse<Any>>
}