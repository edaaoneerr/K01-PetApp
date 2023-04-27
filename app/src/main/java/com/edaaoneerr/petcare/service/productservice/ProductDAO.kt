package com.edaaoneerr.petcare.service.productservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edaaoneerr.petcare.model.Product
import java.util.*

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(vararg product: Product): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Unit

    @Query("SELECT * FROM product")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM product WHERE product_id = :productId")
    suspend fun getProduct(productId: UUID): Product

    @Query("DELETE FROM product")
    suspend fun deleteAllProducts(): Unit

    @Query("DELETE FROM product WHERE product_id = :productId")
    suspend fun deleteProduct(productId: UUID): Unit
}