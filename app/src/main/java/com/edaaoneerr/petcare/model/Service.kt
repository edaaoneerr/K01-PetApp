package com.edaaoneerr.petcare.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull
import java.util.*

@Entity
data class Service(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "service_id")
    @SerializedName("service_id")
    val serviceId: UUID,

    @ColumnInfo(name = "service_name")
    @SerializedName("service_name")
    val serviceName: String?,

    ) {
    constructor(
        serviceName: String?
    ) : this(
        UUID.randomUUID(),
        serviceName
    )
}
