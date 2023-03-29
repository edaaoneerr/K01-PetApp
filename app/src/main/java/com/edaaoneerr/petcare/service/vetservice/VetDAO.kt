package com.edaaoneerr.petcare.service.vetservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edaaoneerr.petcare.model.Vet
import java.util.*

@Dao
interface VetDAO {

    @Insert
    suspend fun insertAllVets(vararg vet: Vet): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVet(vet: Vet)

    @Query("SELECT * FROM vet")
    suspend fun getAllVets(): List<Vet>

    @Query("SELECT * FROM vet WHERE vet_id = :vetId")
    suspend fun getVet(vetId: UUID): Vet

    @Query("DELETE FROM vet")
    suspend fun deleteAllVets(): Unit

    @Query("DELETE FROM vet WHERE vet_id = :vetId")
    suspend fun deleteVet(vetId: UUID): Unit
}