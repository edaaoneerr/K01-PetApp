package com.edaaoneerr.petcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.CampaignListRowBinding
import com.edaaoneerr.petcare.model.Campaign

class CampaignRecyclerAdapter(private val campaignList: ArrayList<Campaign>) :

    RecyclerView.Adapter<CampaignRecyclerAdapter.CampaignViewHolder>() {

    class CampaignViewHolder(val view: CampaignListRowBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CampaignListRowBinding>(
            inflater,
            R.layout.campaign_list_row,
            parent,
            false
        )
        return CampaignViewHolder(view)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        holder.view.campaign = campaignList[position]
    }

    override fun getItemCount(): Int {
        return campaignList.size
    }

    fun updateCampaignList(updatedCampaignList: List<Campaign>) {
        campaignList.clear()
        campaignList.addAll(updatedCampaignList)
        notifyDataSetChanged()

    }
}