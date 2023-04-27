package com.edaaoneerr.petcare.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull
import java.util.*


@Entity
data class Product(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    @SerializedName("product_id")
    val productId: UUID,

    @ColumnInfo(name = "product_name")
    @SerializedName("product_name")
    val productName: String?,

    @ColumnInfo(name = "product_brand")
    @SerializedName("product_brand")
    val productBrand: String?,

    @ColumnInfo(name = "product_price")
    @SerializedName("product_price")
    val productPrice: String?
) {
    constructor(
        productName: String?,
        productPrice: String?,
        productBrand: String?
    ) : this(UUID.randomUUID(), productName, productBrand, productPrice)
}