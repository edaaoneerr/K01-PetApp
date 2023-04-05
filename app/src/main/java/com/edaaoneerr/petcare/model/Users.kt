package com.edaaoneerr.petcare.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull
import java.util.*

@Entity
data class Users(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    val userId: UUID,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val userFullname: String?,

    @ColumnInfo(name = "surname")
    @SerializedName("surname")
    val userSurname: String?,


    @ColumnInfo(name = "user_password")
    @SerializedName("user_password")
    var userPassword: String?,

    @ColumnInfo(name = "is_female")
    @SerializedName("is_female")
    val isFemale: Boolean?,

    @NonNull
    @ColumnInfo(name = "email")
    @SerializedName("email")
    val userEmail: String?,

    @ColumnInfo(name = "phone_number")
    @SerializedName("phone_number")
    val userPhoneNumber: String?,

    @ColumnInfo(name = "address")
    @SerializedName("address")
    val userAddress: String?,

    @ColumnInfo(name = "is_vet")
    @SerializedName("is_vet")
    val isUserVet: Boolean?,
) {
    constructor(userEmail: String, userPassword: String) : this(
        UUID.randomUUID(),
        null,
        null,
        userPassword,
        null,
        userEmail,
        null,
        null,
        false
    )

    constructor(userPhoneNumber: String?) : this(
        UUID.randomUUID(),
        null,
        null,
        null,
        null,
        null,
        userPhoneNumber,
        null,
        false
    )

    constructor(userEmail: String, userPassword: String, userPhoneNumber: String?) : this(
        UUID.randomUUID(),
        null,
        null,
        userPassword,
        null,
        userEmail,
        userPhoneNumber,
        null,
        false
    )

}