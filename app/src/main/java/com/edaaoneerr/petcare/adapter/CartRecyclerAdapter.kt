package com.edaaoneerr.petcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.CartItemRowBinding
import com.edaaoneerr.petcare.model.Product

class CartRecyclerAdapter(private val cartItemList: ArrayList<Product>) :

    RecyclerView.Adapter<CartRecyclerAdapter.CartItemListViewHolder>() {

    class CartItemListViewHolder(val view: CartItemRowBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CartItemRowBinding>(
            inflater,
            R.layout.cart_item_row,
            parent,
            false
        )
        return CartItemListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemListViewHolder, position: Int) {
        holder.view.cartItemName.text = cartItemList[position].productName
        holder.view.cartItemPrice.text = cartItemList[position].productPrice
    }


    override fun getItemCount(): Int {
        return cartItemList.size
    }

    fun updateCart(updatedCartList: List<Product>) {
        cartItemList.clear()
        cartItemList.addAll(updatedCartList)
        notifyDataSetChanged()

    }
}