package com.edaaoneerr.petcare.service.categoryservice

import com.edaaoneerr.petcare.model.Service
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceAPIService {
    private val BASE_URL = "http://10.0.2.2:5000"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ServiceAPI::class.java)

    fun getService(): Single<List<Service>> {
        return api.getService()
    }

    suspend fun getServices(): Response<ResponseBody> {
        return api.getServices()
    }

    suspend fun addService(requestBody: RequestBody): Response<ResponseBody> {
        return api.addService(requestBody)
    }
}