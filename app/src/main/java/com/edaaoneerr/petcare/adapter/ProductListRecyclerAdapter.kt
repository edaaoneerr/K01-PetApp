package com.edaaoneerr.petcare.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.ProductListRowBinding
import com.edaaoneerr.petcare.model.Product

class ProductListRecyclerAdapter(
    private val productList: ArrayList<Product>, context: Context
) :


    RecyclerView.Adapter<ProductListRecyclerAdapter.ProductListViewHolder>() {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("addedProduct", Context.MODE_PRIVATE)

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

        holder.view.addCartButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("addedProduct", productList[position].productId.toString())
            editor.apply()
        }
        holder.view.addCartButton.tag = position
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