package com.edaaoneerr.petcare.view.authview

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.edaaoneerr.petcare.databinding.FragmentLoginBinding
import com.edaaoneerr.petcare.model.Users
import com.edaaoneerr.petcare.view.otpverificationview.OTPVerificationFragment
import com.edaaoneerr.petcare.viewmodel.LoginViewModel
import com.edaaoneerr.petcare.viewmodel.OTPVerificationViewModel
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()
    private val otpViewModel: OTPVerificationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

            val email = binding.userEmailAddress.text.toString()
            val hashedPassword = binding.userPassword.text.toString()
            val user = Users(userEmail = email, userPassword = hashedPassword)
            val phoneNumber = binding.userPhoneNumber.text.toString()
            val otpVerificationFragment = OTPVerificationFragment()
            val bundle = bundleOf("userPhoneNumber" to phoneNumber)
            otpVerificationFragment.arguments = bundle
            otpVerificationFragment.show(parentFragmentManager, "OTPVerificationFragment")

            try {
                otpViewModel.sendSMS(requireActivity(), phoneNumber)
            } catch (e: Exception) {
                Log.e("Exception", e.stackTraceToString())
            }
            loginViewModel.userControl(user)

            loginViewModel.isAuthenticated.observe(viewLifecycleOwner) { isAuthenticated ->
                if (isAuthenticated == true) {
                    val action = LoginFragmentDirections.actionLoginFragmentToHomePageFragment()
                    navigate(action)
                }
            }

        }

    }

    private fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}