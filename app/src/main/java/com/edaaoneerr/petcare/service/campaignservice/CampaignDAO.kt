package com.edaaoneerr.petcare.service.campaignservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edaaoneerr.petcare.model.Campaign
import java.util.*

@Dao
interface CampaignDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCampaigns(vararg campaign: Campaign): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaign(campaign: Campaign): Unit

    @Query("SELECT * FROM campaign")
    suspend fun getCampaigns(): List<Campaign>

    @Query("SELECT * FROM campaign WHERE campaign_id = :campaignId")
    suspend fun getCampaign(campaignId: UUID): Campaign

    @Query("DELETE FROM campaign")
    suspend fun deleteAllCampaigns(): Unit

    @Query("DELETE FROM campaign WHERE campaign_id = :campaignId")
    suspend fun deleteCampaign(campaignId: UUID): Unit
}