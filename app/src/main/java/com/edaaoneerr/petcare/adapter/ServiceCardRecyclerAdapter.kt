package com.edaaoneerr.petcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.ServiceListRowBinding
import com.edaaoneerr.petcare.model.Service

class ServiceCardRecyclerAdapter(private val serviceList: ArrayList<Service>) :

    RecyclerView.Adapter<ServiceCardRecyclerAdapter.ServiceListViewHolder>() {

    class ServiceListViewHolder(val view: ServiceListRowBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ServiceListRowBinding>(
            inflater,
            R.layout.service_list_row,
            parent,
            false
        )
        return ServiceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceListViewHolder, position: Int) {
        holder.view.service = serviceList[position]
        holder.view.imageView.tag = position

    }


    override fun getItemCount(): Int {
        return serviceList.size
    }

    fun updateServiceList(updatedSvList: List<Service>) {
        serviceList.clear()
        serviceList.addAll(updatedSvList)
        notifyDataSetChanged()

    }
}