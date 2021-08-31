package com.example.gopickup.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        const val BASE_URL = "https://api.gaemis.id/"
    }

    private fun provideBasicAuth(): String {
        return Credentials.basic("pickup_mobile", "7069636b75705f6d6f62696c65")
    }

    private fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", provideBasicAuth())
                .build()
            chain.proceed(newRequest)
        }
    }

    private fun provideOkHttpInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return httpLoggingInterceptor
    }

    fun getClient(): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideOkHttpInterceptor())
            .addInterceptor(provideInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}

