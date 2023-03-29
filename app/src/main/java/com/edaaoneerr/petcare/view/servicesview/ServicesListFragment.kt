package com.edaaoneerr.petcare.view.servicesview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.edaaoneerr.petcare.databinding.FragmentServicesListBinding


class ServicesListFragment : Fragment() {

    private lateinit var binding: FragmentServicesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clinic.setOnClickListener {
            val action =
                ServicesListFragmentDirections.actionServicesListFragmentToVetListFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }


}