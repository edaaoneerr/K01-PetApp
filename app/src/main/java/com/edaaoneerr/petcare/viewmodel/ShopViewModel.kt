package com.edaaoneerr.petcare.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.edaaoneerr.petcare.model.Product
import com.edaaoneerr.petcare.service.AppDatabase
import com.edaaoneerr.petcare.service.productservice.ProductAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : BaseViewModel(application) {

    val products = MutableLiveData<List<Product>>()


    private val prodAPIService = ProductAPIService()
    private val disposable = CompositeDisposable()

    fun getProducts() {
        disposable.add(
            prodAPIService.getProduct()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>) {
                        saveToSql(value)
                        Toast.makeText(
                            getApplication(),
                            "Veterinerleri Internetten aldık",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showVets(prodList: List<Product>) {
        products.value = prodList

    }

    private fun saveToSql(productList: List<Product>) {

        launch {
            val dao = AppDatabase(getApplication()).productDao()
            dao.deleteAllProducts()
            val i = 0
            for (i in i..productList.size) {
                productList.toTypedArray()
                if (i == productList.size - 1)
                    break
            }

            dao.insertAllProducts(*productList.toTypedArray())
            showVets(productList)
        }


    }
}