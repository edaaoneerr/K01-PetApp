package com.edaaoneerr.petcare.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.edaaoneerr.petcare.model.Product
import com.edaaoneerr.petcare.service.productservice.ProductAPIService
import io.reactivex.disposables.CompositeDisposable


class CartViewModel(application: Application, context: Context) : BaseViewModel(application) {
    val addedProductList = MutableLiveData<List<Product>>()
    val sharedPreferences =
        context.getSharedPreferences("addedProduct", Context.MODE_PRIVATE)

    private val prodAPIService = ProductAPIService()
    private val disposable = CompositeDisposable()

    /*fun getProducts() {
        launch {
            sharedPreferences.
            val dao = AppDatabase(getApplication()).productDao()
            dao.getProduct()
        }
    }*/
}