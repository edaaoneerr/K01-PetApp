package com.edaaoneerr.petcare.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull
import java.sql.Time
import java.util.*

@Entity
data class Vet(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "vet_id")
    @SerializedName("vet_id")
    var vetId: String,

    @ColumnInfo(name = "vet_name")
    @SerializedName("vet_name")
    var vetName: String?,

    @ColumnInfo(name = "vet_spec")
    @SerializedName("vet_spec")
    val vetSpec: String?, //vet specialty

    @ColumnInfo(name = "vet_loc")
    @SerializedName("vet_loc")
    var vetLoc: String?, //vet location

    @ColumnInfo(name = "vet_day")
    @SerializedName("vet_day")
    var vetDay: String?,

    @ColumnInfo(name = "vet_hour")
    @SerializedName("vet_hour")
    var vetHour: String?, //vet location

)