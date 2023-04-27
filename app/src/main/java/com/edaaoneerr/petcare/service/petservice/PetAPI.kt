package com.edaaoneerr.petcare.service.petservice

import com.edaaoneerr.petcare.model.Pet
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PetAPI {
    @GET("/pet")
    fun getPet(): Single<List<Pet>>

    @GET("/pet")
    suspend fun getPets(): Response<ResponseBody>

    @POST("/pet")
    suspend fun addPet(@Body requestBody: RequestBody): Response<ResponseBody>
}