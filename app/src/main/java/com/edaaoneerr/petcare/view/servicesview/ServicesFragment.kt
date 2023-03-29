package com.edaaoneerr.petcare.view.servicesview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.FragmentServicesBinding


class ServicesFragment : Fragment() {

    private lateinit var binding: FragmentServicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val action = ServicesFragmentDirections.actionServicesMenuToServicesListFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

}