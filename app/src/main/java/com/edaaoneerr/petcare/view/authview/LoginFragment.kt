package com.edaaoneerr.petcare.view.authview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.edaaoneerr.petcare.databinding.FragmentLoginBinding
import com.edaaoneerr.petcare.model.Users
import com.edaaoneerr.petcare.util.navigate
import com.edaaoneerr.petcare.view.otpverificationview.OTPVerificationFragment
import com.edaaoneerr.petcare.viewmodel.AuthenticationResult.*
import com.edaaoneerr.petcare.viewmodel.LoginViewModel
import com.edaaoneerr.petcare.viewmodel.OTPVerificationViewModel


class LoginFragment : Fragment() {

    // Use lateinit instead of nullable types for view bindings.
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()
    private val otpViewModel: OTPVerificationViewModel by viewModels()
    private val actionToHomePage = LoginFragmentDirections.actionLoginFragmentToHomePageFragment()
    private lateinit var user: Users

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //requestHint()
        binding.signUpNowCheckedText.setOnClickListener { userWantedToSignIn() }
        binding.loginButton.setOnClickListener { userAuthenticate() }

    }


    private fun userWantedToSignIn() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        navigate(binding.root, action)
    }

    private fun userAuthenticate() {
        val phoneNumber = binding.userPhoneNumber.text.toString()
        val email = binding.userEmailAddress.text.toString()
        val hashedPassword = binding.userPassword.text.toString()

        if (phoneNumber.isEmpty()) {
            user = Users(email, hashedPassword)
            loginViewModel.authenticateUser(user)
        } else {
            user = Users(email, hashedPassword, phoneNumber)
            loginViewModel.authenticateUser(user)
        }
        checkAuthenticationResult(phoneNumber)
    }

    private fun checkAuthenticationResult(phoneNumber: String) {
        loginViewModel.authenticationResult
            .observe(viewLifecycleOwner, Observer { authenticationResult ->
                when (authenticationResult) {
                    PHONE_AUTHENTICATED -> {
                        showOTPVerificationDialog(phoneNumber)
                        saveAuthenticationStatus(true)
                    }
                    FULLY_AUTHENTICATED -> {
                        showOTPVerificationDialog(phoneNumber)
                        saveAuthenticationStatus(true)

                    }
                    EMAIL_AUTHENTICATED -> {
                        navigate(binding.root, actionToHomePage)
                        saveAuthenticationStatus(true)
                    }
                    INVALID_CREDENTIALS -> {
                        Toast.makeText(context, "Yanlış parola.", Toast.LENGTH_LONG).show()
                        saveAuthenticationStatus(false)
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            "Böyle bir kullanıcı bulunamadı.",
                            Toast.LENGTH_LONG
                        ).show()
                        saveAuthenticationStatus(false)
                    }
                }
            })
    }

    private fun showOTPVerificationDialog(phoneNumber: String) {
        // Show the OTP verification fragment with the user's phone number.
        val otpVerificationFragment = OTPVerificationFragment()
        val bundle = bundleOf("userPhoneNumber" to phoneNumber)
        otpVerificationFragment.arguments = bundle
        otpVerificationFragment.show(parentFragmentManager, "OTPVerificationFragment")
    }

    private fun saveAuthenticationStatus(status: Boolean) {
        val sharedPreferences =
            this.requireActivity().getSharedPreferences("Authentication", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isAuthenticated", status).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}