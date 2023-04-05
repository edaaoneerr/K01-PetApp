package com.edaaoneerr.petcare.view.authview

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity


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
        requestHint()
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

        binding.signUpNowCheckedText.setOnClickListener { userWantedToSignIn() }
        binding.loginButton.setOnClickListener { userAuthenticate() }

    }

    private fun requestHint() {

        val request: GetPhoneNumberHintIntentRequest =
            GetPhoneNumberHintIntentRequest.builder().build()
        val phoneNumberHintIntentResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
            registerForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult()
            ) { result ->
                try {
                    val phoneNumber = Identity.getSignInClient(requireActivity())
                        .getPhoneNumberFromIntent(result.data)
                    binding.userPhoneNumber.setText(phoneNumber)
                } catch (e: Exception) {
                    e.message?.let { message ->
                        Log.d(ContentValues.TAG, message)
                    }
                }
            }
        Identity.getSignInClient(requireActivity())
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener {
                phoneNumberHintIntentResultLauncher.launch(
                    IntentSenderRequest.Builder(it.intentSender).build()
                )
            }
            .addOnFailureListener {
                it.message?.let { message ->
                    Log.d(ContentValues.TAG, message)
                }
            }
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

                    }
                    FULLY_AUTHENTICATED -> {
                        showOTPVerificationDialog(phoneNumber)

                    }
                    EMAIL_AUTHENTICATED -> {
                        navigate(binding.root, actionToHomePage)
                    }
                    INVALID_CREDENTIALS -> {
                        Toast.makeText(context, "Yanlış parola.", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            "Böyle bir kullanıcı bulunamadı.",
                            Toast.LENGTH_LONG
                        ).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}