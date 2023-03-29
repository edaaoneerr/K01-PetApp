package com.edaaoneerr.petcare.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edaaoneerr.petcare.model.Users
import com.edaaoneerr.petcare.model.Vet
import com.edaaoneerr.petcare.service.userservice.UserDAO
import com.edaaoneerr.petcare.service.vetservice.VetDAO


    @Database(entities = [Vet::class, Users::class], version = 1, exportSchema = false)

    abstract class AppDatabase : RoomDatabase() {
        abstract fun vetDao(): VetDAO
        abstract fun userDao(): UserDAO


        companion object {

            //diger threadlere gorunumunu vermesi icin volatile
            @Volatile private var instance: AppDatabase? = null

            private val lock = Any()

            operator fun invoke(context: Context) = instance?: synchronized(lock){
                instance?: databaseOlustur(context).also {
                    instance = it
                }
            }


            private fun databaseOlustur(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "myPet").build()


        }
    }


