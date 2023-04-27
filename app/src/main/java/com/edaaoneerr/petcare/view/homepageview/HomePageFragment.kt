package com.edaaoneerr.petcare.view.homepageview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.edaaoneerr.petcare.adapter.CampaignRecyclerAdapter
import com.edaaoneerr.petcare.adapter.NearbyVetRecyclerAdapter
import com.edaaoneerr.petcare.adapter.ServiceCardRecyclerAdapter
import com.edaaoneerr.petcare.databinding.FragmentHomePageBinding
import com.edaaoneerr.petcare.viewmodel.HomePageViewModel
import com.edaaoneerr.petcare.viewmodel.VetListViewModel


class HomePageFragment : Fragment() {

    private lateinit var navController: NavController
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!


    private val homePageViewModel: HomePageViewModel by viewModels()
    private val vetListViewModel: VetListViewModel by viewModels()

    private var serviceRecyclerAdapter = ServiceCardRecyclerAdapter(arrayListOf())
    private var campaignRecyclerAdapter = CampaignRecyclerAdapter(arrayListOf())
    private var nearbyVetRecyclerAdapter = NearbyVetRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            })


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)



        binding.serviceListRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.serviceListRecyclerView.adapter = serviceRecyclerAdapter
        this.binding.serviceListRecyclerView.visibility = View.VISIBLE


        binding.campaignRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.campaignRecyclerView.adapter = campaignRecyclerAdapter
        this.binding.campaignRecyclerView.visibility = View.VISIBLE

        binding.nearbyVetRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.nearbyVetRecyclerView.adapter = nearbyVetRecyclerAdapter
        this.binding.nearbyVetRecyclerView.visibility = View.VISIBLE


        homePageViewModel.getCampaigns()
        homePageViewModel.campaigns.observe(viewLifecycleOwner) {
            it?.let {
                campaignRecyclerAdapter.updateCampaignList(it)
            }

        }

        homePageViewModel.getServices()
        homePageViewModel.services.observe(viewLifecycleOwner) {
            it?.let {
                serviceRecyclerAdapter.updateServiceList(it)
            }

        }

        vetListViewModel.vets.observe(viewLifecycleOwner) {
            it?.let {
                nearbyVetRecyclerAdapter.updateNearbyVetList(it)
            }

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}