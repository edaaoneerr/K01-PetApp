package com.edaaoneerr.petcare.service.campaignservice

import com.edaaoneerr.petcare.model.Campaign
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CampaignAPI {
    @GET("/campaign")
    fun getCampaign(): Single<List<Campaign>>

    @GET("/campaign")
    suspend fun getCampaigns(): Response<ResponseBody>

    @POST("/campaign")
    suspend fun addCampaign(@Body requestBody: RequestBody): Response<ResponseBody>
}