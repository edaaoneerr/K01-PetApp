package com.edaaoneerr.petcare.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edaaoneerr.petcare.model.*
import com.edaaoneerr.petcare.service.campaignservice.CampaignDAO
import com.edaaoneerr.petcare.service.categoryservice.ServiceDAO
import com.edaaoneerr.petcare.service.petservice.PetDAO
import com.edaaoneerr.petcare.service.productservice.ProductDAO
import com.edaaoneerr.petcare.service.userservice.UserDAO
import com.edaaoneerr.petcare.service.vetservice.VetDAO


@Database(
    entities = [Vet::class, Users::class, Product::class, Pet::class, Campaign::class, Service::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun vetDao(): VetDAO
    abstract fun userDao(): UserDAO
    abstract fun productDao(): ProductDAO
    abstract fun petDao(): PetDAO
    abstract fun serviceDao(): ServiceDAO
    abstract fun campaignDao(): CampaignDAO


    companion object {

        //diger threadlere gorunumunu vermesi icin volatile
        @Volatile
        private var instance: AppDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }


        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "myPet"
        ).build()


    }
}


