package com.edaaoneerr.petcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.NearbyVetListRowBinding
import com.edaaoneerr.petcare.model.Vet

class NearbyVetRecyclerAdapter(private val nearbyVetList: ArrayList<Vet>) :

    RecyclerView.Adapter<NearbyVetRecyclerAdapter.NearbyVetViewHolder>() {

    class NearbyVetViewHolder(val view: NearbyVetListRowBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyVetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<NearbyVetListRowBinding>(
            inflater,
            R.layout.nearby_vet_list_row,
            parent,
            false
        )
        return NearbyVetViewHolder(view)
    }

    override fun onBindViewHolder(holder: NearbyVetViewHolder, position: Int) {
        holder.view.vet = nearbyVetList[position]
    }

    override fun getItemCount(): Int {
        return nearbyVetList.size
    }

    fun updateNearbyVetList(updatedVetList: List<Vet>) {
        nearbyVetList.clear()
        nearbyVetList.addAll(updatedVetList)
        notifyDataSetChanged()

    }
}