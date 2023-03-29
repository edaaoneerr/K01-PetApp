package com.edaaoneerr.petcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.VetListRowBinding
import com.edaaoneerr.petcare.model.Vet

class VetListRecyclerAdapter(private val vetList: ArrayList<Vet>):

    RecyclerView.Adapter<VetListRecyclerAdapter.VetListViewHolder>() {

    class VetListViewHolder(val view: VetListRowBinding): RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VetListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<VetListRowBinding>(inflater, R.layout.vet_list_row, parent, false)
        return VetListViewHolder(view)
    }

    override fun onBindViewHolder(holder: VetListViewHolder, position: Int) {
        holder.view.vet = vetList[position]
        println("On bind view holder")
        println(holder.view.vet)
        println(vetList[position])

    }



    override fun getItemCount(): Int {
        return vetList.size
    }

    fun updateVetList(updatedVetList: List<Vet>){
        vetList.clear()
        vetList.addAll(updatedVetList)
        notifyDataSetChanged()

    }

}