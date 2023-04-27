package com.edaaoneerr.petcare.service.campaignservice

import com.edaaoneerr.petcare.model.Campaign
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CampaignAPIService {
    private val BASE_URL = "http://10.0.2.2:5000"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CampaignAPI::class.java)

    fun getCampaign(): Single<List<Campaign>> {
        return api.getCampaign()
    }

    suspend fun getCampaigns(): Response<ResponseBody> {
        return api.getCampaigns()
    }

    suspend fun addCampaign(requestBody: RequestBody): Response<ResponseBody> {
        return api.addCampaign(requestBody)
    }
}