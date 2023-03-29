package com.edaaoneerr.petcare.view.vetview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.adapter.VetListRecyclerAdapter
import com.edaaoneerr.petcare.databinding.FragmentVetListBinding
import com.edaaoneerr.petcare.model.Vet

import com.edaaoneerr.petcare.viewmodel.VetListViewModel

class VetListFragment : Fragment() {

    private lateinit var binding: FragmentVetListBinding

    private val vetListViewModel: VetListViewModel by viewModels()
    private  var vetRecyclerAdapter = VetListRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vet_list, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vetListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.vetListRecyclerView.adapter = vetRecyclerAdapter
        this.binding.vetListRecyclerView.visibility = View.VISIBLE
        vetListViewModel.getVets()
        vetListViewModel.vets.observe(viewLifecycleOwner) {
            it?.let {
                vetRecyclerAdapter.updateVetList(it)
            }

        }

    }



}