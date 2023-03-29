package com.edaaoneerr.petcare.service.vetservice

import com.edaaoneerr.petcare.model.Vet
import io.reactivex.Single
import retrofit2.http.GET

interface VetAPI {

    @GET("/vet")
    fun getVet(): Single<List<Vet>>
}