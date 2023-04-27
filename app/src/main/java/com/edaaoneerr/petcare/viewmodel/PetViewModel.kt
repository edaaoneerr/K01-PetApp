package com.edaaoneerr.petcare.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.edaaoneerr.petcare.model.Pet
import com.edaaoneerr.petcare.service.AppDatabase
import com.edaaoneerr.petcare.service.petservice.PetAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class PetViewModel(application: Application) : BaseViewModel(application) {

    val pets = MutableLiveData<List<Pet>>()


    private val petAPIService = PetAPIService()
    private val disposable = CompositeDisposable()

    fun getPets() {
        disposable.add(
            petAPIService.getProduct()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Pet>>() {
                    override fun onSuccess(value: List<Pet>) {
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

    private fun showPets(petList: List<Pet>) {
        pets.value = petList

    }

    private fun saveToSql(petList: List<Pet>) {

        launch {
            val dao = AppDatabase(getApplication()).petDao()
            dao.deleteAllPets()
            val i = 0
            for (i in i..petList.size) {
                petList.toTypedArray()
                if (i == petList.size - 1)
                    break
            }

            dao.insertAllPets(*petList.toTypedArray())
            showPets(petList)
        }


    }
}