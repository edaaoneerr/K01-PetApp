package com.edaaoneerr.petcare.service.petservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edaaoneerr.petcare.model.Pet
import java.util.*

@Dao
interface PetDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPets(vararg pet: Pet): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet): Unit

    @Query("SELECT * FROM pet")
    suspend fun getPets(): List<Pet>

    @Query("SELECT * FROM pet WHERE pet_id = :petId")
    suspend fun getPet(petId: UUID): Pet

    @Query("DELETE FROM pet")
    suspend fun deleteAllPets(): Unit

    @Query("DELETE FROM pet WHERE pet_id = :petId")
    suspend fun deletePet(petId: UUID): Unit
}