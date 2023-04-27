package com.edaaoneerr.petcare.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.edaaoneerr.petcare.model.Campaign
import com.edaaoneerr.petcare.model.Service
import com.edaaoneerr.petcare.service.AppDatabase
import com.edaaoneerr.petcare.service.campaignservice.CampaignAPIService
import com.edaaoneerr.petcare.service.categoryservice.ServiceAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomePageViewModel(application: Application) : BaseViewModel(application) {

    val services = MutableLiveData<List<Service>>()
    val campaigns = MutableLiveData<List<Campaign>>()


    private val serviceAPIService = ServiceAPIService()
    private val campaignAPIService = CampaignAPIService()
    private val disposable = CompositeDisposable()

    fun getCampaigns() {
        disposable.add(
            campaignAPIService.getCampaign()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Campaign>>() {
                    override fun onSuccess(value: List<Campaign>) {
                        saveCampaigns(value)
                        Toast.makeText(
                            getApplication(),
                            "Kampanyaları Internetten aldık",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getServices() {
        disposable.add(
            serviceAPIService.getService()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Service>>() {
                    override fun onSuccess(value: List<Service>) {
                        saveServices(value)
                        Toast.makeText(
                            getApplication(),
                            "Servisleri Internetten aldık",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showServices(serviceList: List<Service>) {
        services.value = serviceList

    }

    private fun showCampaigns(campaignList: List<Campaign>) {
        campaigns.value = campaignList

    }

    private fun saveServices(serviceList: List<Service>) {

        launch {
            val dao = AppDatabase(getApplication()).serviceDao()
            dao.deleteAllServices()
            val i = 0
            for (i in i..serviceList.size) {
                serviceList.toTypedArray()
                if (i == serviceList.size - 1)
                    break
            }

            dao.insertAllServices(*serviceList.toTypedArray())
            showServices(serviceList)
        }


    }

    private fun saveCampaigns(campaignList: List<Campaign>) {

        launch {
            val dao = AppDatabase(getApplication()).campaignDao()
            dao.deleteAllCampaigns()
            val i = 0
            for (i in i..campaignList.size) {
                campaignList.toTypedArray()
                if (i == campaignList.size - 1)
                    break
            }

            dao.insertAllCampaigns(*campaignList.toTypedArray())
            showCampaigns(campaignList)
        }


    }
}