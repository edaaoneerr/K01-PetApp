package com.edaaoneerr.petcare.view.authview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.edaaoneerr.petcare.databinding.FragmentLoginBinding
import com.edaaoneerr.petcare.model.Users
import com.edaaoneerr.petcare.view.homepageview.HomePageFragment
import com.edaaoneerr.petcare.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpNowCheckedText.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.loginButton.setOnClickListener {

            println(loginViewModel.isAuthenticated)
            val email = binding.userEmailAddress.text.toString()
            val hashedPassword = binding.userPassword.text.toString()
            val user = Users(userEmail = email, userPassword = hashedPassword)


            loginViewModel.userControl(user)

            loginViewModel.isAuthenticated.observe(viewLifecycleOwner) { isAuthenticated ->
                if (isAuthenticated == true) {
                    val action = LoginFragmentDirections.actionLoginFragmentToHomePageFragment()
                    navigate(action)
                }
            }
        }

        }

      fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }
       override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    }