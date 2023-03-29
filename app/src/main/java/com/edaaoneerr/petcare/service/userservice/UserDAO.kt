package com.edaaoneerr.petcare.service.userservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edaaoneerr.petcare.model.Users
import java.util.*

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(vararg user: Users): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Users): Unit

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<Users>

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUser(userId: UUID): Users

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers(): Unit

    @Query("DELETE FROM users WHERE user_id = :userId")
    suspend fun deleteUser(userId: UUID): Unit
}