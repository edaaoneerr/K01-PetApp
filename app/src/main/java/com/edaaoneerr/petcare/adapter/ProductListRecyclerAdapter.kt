package com.edaaoneerr.petcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.ProductListRowBinding
import com.edaaoneerr.petcare.model.Product

class ProductListRecyclerAdapter(private val productList: ArrayList<Product>) :

    RecyclerView.Adapter<ProductListRecyclerAdapter.ProductListViewHolder>() {

    class ProductListViewHolder(val view: ProductListRowBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ProductListRowBinding>(
            inflater,
            R.layout.product_list_row,
            parent,
            false
        )
        return ProductListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.view.product = productList[position]
        println("On bind view holder")
        println(holder.view.product)
        println(productList[position])

    }


    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateProductList(updatedProdList: List<Product>) {
        productList.clear()
        productList.addAll(updatedProdList)
        notifyDataSetChanged()

    }

}