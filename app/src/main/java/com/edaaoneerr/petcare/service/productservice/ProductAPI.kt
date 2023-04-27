package com.edaaoneerr.petcare.service.productservice

import com.edaaoneerr.petcare.model.Product
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductAPI {
    @GET("/product")
    fun getProduct(): Single<List<Product>>

    @GET("/product")
    suspend fun getProducts(): Response<ResponseBody>

    @POST("/product")
    suspend fun addProduct(@Body requestBody: RequestBody): Response<ResponseBody>
}