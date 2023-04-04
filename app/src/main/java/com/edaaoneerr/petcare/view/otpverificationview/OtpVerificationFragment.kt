package com.edaaoneerr.petcare.view.otpverificationview

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.databinding.FragmentOtpVerificationBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        otpViewModel.countdown.observe(viewLifecycleOwner) { count ->
            if (count == 0) {
                binding.resendButton.isEnabled = true
                binding.verifyButton.isEnabled = false
            } else {
                binding.resendButton.isEnabled = false
                binding.verifyButton.isEnabled = true
                binding.timerTextView.text = count.toString()
            }
        }

        otpViewModel.startCountdown()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentOtpVerificationBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireActivity())
        val dialogView = layoutInflater.inflate(R.layout.fragment_otp_verification, null)

        val userPhoneNumber = arguments?.getString("userPhoneNumber")


        dialogView.findViewById<Button>(R.id.resendButton).setOnClickListener {
            otpViewModel.sendSMS(it.context, userPhoneNumber.toString())
        }

        dialogView.findViewById<Button>(R.id.verifyButton).setOnClickListener {
            val enteredOtp = dialogView.findViewById<OTPView>(R.id.otpView).getStringFromFields()

            if (otpViewModel.verifyOTP(
                    it.context,
                    userPhoneNumber.toString(),
                    enteredOtp
                )
            ) {
                Toast.makeText(requireContext(), "OTP VERIFIED", Toast.LENGTH_LONG).show()
                val action =
                    OTPVerificationFragmentDirections.actionOTPVerificationFragmentToHomeMenu()
                navigate(action)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "TEKRAR DENEYİN", Toast.LENGTH_LONG).show()
            }
        }

        return builder.setView(dialogView).create()

    }

    private fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }


}