package com.edaaoneerr.petcare.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.edaaoneerr.petcare.model.Vet
import com.edaaoneerr.petcare.service.AppDatabase
import com.edaaoneerr.petcare.service.vetservice.VetAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class VetListViewModel(application: Application): BaseViewModel(application) {

    val vets = MutableLiveData<List<Vet>>()
    val vetError = MutableLiveData<Boolean>()
    val vetLoading = MutableLiveData<Boolean>()


    private val vetAPIService = VetAPIService()
    private val disposable = CompositeDisposable()

     fun getVets() {
        disposable.add(
            vetAPIService.getVetData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Vet>>()
                {
                    override fun onSuccess(value: List<Vet>) {
                        saveToSql(value)
                        Toast.makeText(getApplication(), "Veterinerleri Internetten aldık", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        vetError.value = true
                        vetLoading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

     private fun showVets(vetList: List<Vet>) {
        vets.value = vetList
        vetLoading.value = false
        vetError.value = false

    }

    private fun saveToSql(vetList: List<Vet>){

        launch {
            val dao = AppDatabase(getApplication()).vetDao()
            dao.deleteAllVets()
            val i = 0
            for (i in i.. vetList.size){
                vetList.toTypedArray()
                if (i == vetList.size - 1)
                    break
            }

            dao.insertAllVets(*vetList.toTypedArray())
            showVets(vetList)
        }
        }

    }




