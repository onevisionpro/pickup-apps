package com.example.gopickup.model.repository

import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.response.User
import io.reactivex.Observable

interface AppRepository {

    fun postLoginAndOTP(login: Login): Observable<BaseResponse<User>>

    fun postOTP(login: Login): Observable<BaseResponse<User>>
}