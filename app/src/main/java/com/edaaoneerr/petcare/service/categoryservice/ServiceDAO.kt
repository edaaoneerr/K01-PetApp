package com.edaaoneerr.petcare.service.categoryservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edaaoneerr.petcare.model.Service
import java.util.*

@Dao
interface ServiceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllServices(vararg service: Service): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: Service): Unit

    @Query("SELECT * FROM service")
    suspend fun getServices(): List<Service>

    @Query("SELECT * FROM service WHERE service_id = :serviceId")
    suspend fun getService(serviceId: UUID): Service

    @Query("DELETE FROM service")
    suspend fun deleteAllServices(): Unit

    @Query("DELETE FROM service WHERE service_id = :serviceId")
    suspend fun deleteService(serviceId: UUID): Unit
}