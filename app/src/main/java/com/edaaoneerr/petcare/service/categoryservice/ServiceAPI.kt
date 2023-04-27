package com.edaaoneerr.petcare.service.categoryservice

import com.edaaoneerr.petcare.model.Service
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceAPI {
    @GET("/service")
    fun getService(): Single<List<Service>>

    @GET("/service")
    suspend fun getServices(): Response<ResponseBody>

    @POST("/service")
    suspend fun addService(@Body requestBody: RequestBody): Response<ResponseBody>
}