package com.edaaoneerr.petcare.service.userservice

import com.edaaoneerr.petcare.model.Users
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {
    @GET("/user")
    fun getUser(): Single<List<Users>>

    @GET("/user")
    suspend fun getUsers(): Response<ResponseBody>

    @POST("/user")
    suspend fun postUser(@Body requestBody: RequestBody): Response<ResponseBody>
}