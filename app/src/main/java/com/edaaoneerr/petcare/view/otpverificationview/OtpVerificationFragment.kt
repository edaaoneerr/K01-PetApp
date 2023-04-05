package com.edaaoneerr.petcare.view.otpverificationview

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.FragmentOtpVerificationBinding
import com.edaaoneerr.petcare.util.navigate
import com.edaaoneerr.petcare.view.authview.LoginFragmentDirections
import com.edaaoneerr.petcare.viewmodel.OTPVerificationResult
import com.edaaoneerr.petcare.viewmodel.OTPVerificationViewModel
import com.kevinschildhorn.otpview.OTPView


class OTPVerificationFragment : DialogFragment() {

    private lateinit var binding: FragmentOtpVerificationBinding
    private val otpViewModel: OTPVerificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpVerificationBinding.inflate(inflater, container, false)
        val navController = findNavController()
        Navigation.setViewNavController(binding.otpVerificationContainer, navController)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FragmentOtpVerificationBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireActivity())
        val dialogView = layoutInflater.inflate(R.layout.fragment_otp_verification, null)
        val userPhoneNumber = arguments?.getString("userPhoneNumber")
        otpViewModel.sendSMS(requireContext(), userPhoneNumber.toString())

        dialogView.findViewById<Button>(R.id.resendButton).setOnClickListener {
            otpViewModel.sendSMS(it.context, userPhoneNumber.toString())
            otpViewModel.startCountdown()
        }

        dialogView.findViewById<Button>(R.id.verifyButton).setOnClickListener {
            val enteredOtp = dialogView.findViewById<OTPView>(R.id.otpView).getStringFromFields()
            verifyOTP(enteredOtp)
        }

        otpViewModel.countdown.observe(this) { countdown ->
            dialogView.findViewById<TextView>(R.id.timerTextView).text =
                "Yeniden denemek için kalan süre: $countdown saniye"

            if (otpViewModel.countdown.value == 0) {
                otpViewModel.sendSMS(binding.root.context, userPhoneNumber.toString())
                otpViewModel.startCountdown()
            }
        }

        return builder.setView(dialogView).create()

    }


    private fun verifyOTP(enteredOtp: String): Boolean {

        if (otpViewModel.verificationCode.value == enteredOtp) {
            otpViewModel.otpVerificationResult.value = OTPVerificationResult.OTP_VERIFIED

            val action = LoginFragmentDirections.actionLoginFragmentToHomePageFragment()
            navigate(binding.root, action)

            dismiss()
        } else {
            otpViewModel.otpVerificationResult.value = OTPVerificationResult.INVALID_OTP
            Toast.makeText(requireContext(), "TEKRAR DENEYİN", Toast.LENGTH_LONG).show()
        }

        return false
    }

}