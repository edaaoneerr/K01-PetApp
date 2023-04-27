package com.edaaoneerr.petcare.service.productservice

import com.edaaoneerr.petcare.model.Product
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ProductAPIService {
    private val BASE_URL = "http://10.0.2.2:5000"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ProductAPI::class.java)

    fun getProduct(): Single<List<Product>> {
        return api.getProduct()
    }

    suspend fun getProducts(): Response<ResponseBody> {
        return api.getProducts()
    }

    suspend fun createUser(requestBody: RequestBody): Response<ResponseBody> {
        return api.addProduct(requestBody)
    }

}