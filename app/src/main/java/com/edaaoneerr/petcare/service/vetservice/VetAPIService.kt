package com.edaaoneerr.petcare.service.vetservice

import com.edaaoneerr.petcare.model.Vet
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class VetAPIService {

    private val BASE_URL = "http://10.0.2.2:5000"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(VetAPI::class.java)

    fun getVetData(): Single<List<Vet>> {
        return api.getVet()
    }
}