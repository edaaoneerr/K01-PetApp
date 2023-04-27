package com.edaaoneerr.petcare.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull
import java.util.*

@Entity
data class Pet(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "pet_id")
    @SerializedName("pet_id")
    val petId: UUID,

    @ColumnInfo(name = "pet_name")
    @SerializedName("pet_name")
    val petName: String?,

    @ColumnInfo(name = "pet_age")
    @SerializedName("pet_age")
    val petAge: String?,

    @ColumnInfo(name = "is_female")
    @SerializedName("is_female")
    val petFemale: Boolean?,

    @ColumnInfo(name = "pet_type")
    @SerializedName("pet_type")
    val petType: String?,

    @ColumnInfo(name = "pet_breed")
    @SerializedName("product_breed")
    val petBreed: String?,

    @ColumnInfo(name = "owner_id")
    @SerializedName("product_price")
    val ownerId: UUID?,

    @ColumnInfo(name = "vet_id")
    @SerializedName("product_price")
    val vetId: UUID?
) {
    constructor(
        petName: String?,
        petAge: String?,
        petFemale: Boolean?,
        petType: String?,
        petBreed: String?,
        ownerId: UUID?,
        vetId: UUID?,
    ) : this(UUID.randomUUID(), petName, petAge, petFemale, petType, petBreed, ownerId, vetId)
}