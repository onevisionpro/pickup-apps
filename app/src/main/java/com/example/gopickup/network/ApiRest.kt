package com.example.gopickup.network

import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.Login
import com.example.gopickup.model.response.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiRest {

    @POST("login")
    fun postLogin(@Body login: Login) : Observable<BaseResponse<User>>

    @POST("login")
    fun postOTP(@Body login: Login) : Observable<BaseResponse<User>>
}