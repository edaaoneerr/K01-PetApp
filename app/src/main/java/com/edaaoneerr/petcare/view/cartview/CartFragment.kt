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
import com.edaaoneerr.petcare.adapter.ProductListRecyclerAdapter
import com.edaaoneerr.petcare.databinding.FragmentCartBinding
import com.edaaoneerr.petcare.viewmodel.CartViewModel


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private var productRecyclerAdapter = ProductListRecyclerAdapter(arrayListOf())

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

        binding.productListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.productListRecyclerView.adapter = productRecyclerAdapter
        this.binding.productListRecyclerView.visibility = View.VISIBLE
        cartViewModel.getProducts()
        cartViewModel.products.observe(viewLifecycleOwner) {
            it?.let {
                productRecyclerAdapter.updateProductList(it)
            }

        }
    }

}