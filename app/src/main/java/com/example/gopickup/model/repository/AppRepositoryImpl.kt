package com.example.gopickup.model.repository

import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.response.User
import com.example.gopickup.network.ApiRest
import io.reactivex.Observable

class AppRepositoryImpl(private val apiRest: ApiRest) : AppRepository {

    override fun postLoginAndOTP(login: Login): Observable<BaseResponse<Any>> {
        return apiRest.postLogin(login)
    }

    override fun postOTP(login: Login): Observable<BaseResponse<User>> {
        return apiRest.postOTP(login)
    }

}