package com.edaaoneerr.petcare.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Campaign(
    @PrimaryKey
    @ColumnInfo(name = "campaign_id")
    @SerializedName("campaign_id")
    val campaignId: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "campaign_name")
    @SerializedName("campaign_name")
    val campaignName: String?,

    @ColumnInfo(name = "campaign_image_url")
    @SerializedName("campaign_image_url")
    val campaignImageUrl: String?,

    @ColumnInfo(name = "campaign_description")
    @SerializedName("campaign_description")
    val campaignDescription: String?,

    @ColumnInfo(name = "campaign_discount_rate")
    @SerializedName("campaign_discount_rate")
    val campaignDiscountRate: Double?,

    @ColumnInfo(name = "campaign_start_date")
    @SerializedName("campaign_start_date")
    val campaignStartDate: String?,

    @ColumnInfo(name = "campaign_end_date")
    @SerializedName("campaign_end_date")
    val campaignEndDate: String?,

    @ColumnInfo(name = "campaign_vet_id")
    @SerializedName("campaign_vet_id")
    val campaignVetId: UUID?
) {
    constructor(
        campaignName: String?,
        campaignImageUrl: String?,
        campaignDescription: String?,
        campaignDiscountRate: Double?,
        campaignStartDate: String?,
        campaignEndDate: String?,
        campaignVetId: UUID?,
    ) : this(
        UUID.randomUUID(),
        campaignName,
        campaignImageUrl,
        campaignDescription,
        campaignDiscountRate,
        campaignStartDate,
        campaignEndDate,
        campaignVetId
    )
}
