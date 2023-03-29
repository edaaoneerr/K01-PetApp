package com.edaaoneerr.petcare.service.userservice

import com.edaaoneerr.petcare.model.Users
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserAPIService {
    private val BASE_URL = "http://10.0.2.2:5000"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(UserAPI::class.java)

    fun getUserData(): Single<List<Users>> {
        return api.getUser()
    }

    suspend fun getUsers(): Response<ResponseBody> {
        return api.getUsers()
    }

    suspend fun createUser(requestBody: RequestBody): Response<ResponseBody> {
        return api.postUser(requestBody)
    }


}