package com.edaaoneerr.petcare.view.cartview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.adapter.CartRecyclerAdapter
import com.edaaoneerr.petcare.databinding.FragmentCartBinding
import com.edaaoneerr.petcare.viewmodel.CartViewModel
import com.edaaoneerr.petcare.viewmodel.ShopViewModel


class CartFragment : Fragment() {

    private var cartRecyclerAdapter = CartRecyclerAdapter(arrayListOf())
    private val shopViewModel: ShopViewModel by viewModels()

    val cartViewModel: CartViewModel by viewModels()

    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        for (product in shopViewModel.products.value!!) {
            if (product.productId.toString() == addedProduct) {
                cartViewModel.addedProductList.value = arrayListOf(product)
                cartViewModel.addedProductList.observe(viewLifecycleOwner) {
                    it?.let {
                        cartRecyclerAdapter.updateCart(it)
                    }
                }
            }
        }*/


        binding.cartItemRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.cartItemRecyclerView.adapter = cartRecyclerAdapter
        this.binding.cartItemRecyclerView.visibility = View.VISIBLE


    }

}