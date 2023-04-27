package com.edaaoneerr.petcare.view.petview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.FragmentPetBinding
import com.edaaoneerr.petcare.viewmodel.PetViewModel


class PetFragment : Fragment() {
    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!
    private val petViewModel: PetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetBinding.inflate(inflater, container, false)
        binding.root.setPadding(0, resources.getDimensionPixelSize(R.dimen.toolbar_height), 0, 0)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mSpinner = binding.animalType

        // Create a list to display in the Spinner
        val mList = resources.getStringArray(R.array.petType)

        // Create an adapter as shown below
        val mArrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_list, mList)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)

        // Set the adapter to the Spinner
        mSpinner.adapter = mArrayAdapter

        //petViewModel.getPets()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}