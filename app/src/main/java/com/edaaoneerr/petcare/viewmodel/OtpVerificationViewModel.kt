package com.edaaoneerr.petcare.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.CountDownTimer
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class OTPVerificationViewModel : ViewModel() {

    private var countdownTimer: CountDownTimer? = null
    private val countdownInterval = 1000L // 1 second
    private val countdownDuration = 120000L // 2 minutes
    private var countdownSecondsRemaining = 0
    val verificationCode = MutableLiveData<String?>()
    val countdown = MutableLiveData<Int>()
    val otpVerificationResult = MutableLiveData<OTPVerificationResult>()


    private fun generateVerificationCode(): String {
        val random = Random()
        val code = random.nextInt(999999 - 100000) + 10000
        return code.toString()
    }

    fun sendSMS(context: Context, phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            _sendSMS(phoneNumber)
            startCountdown()

        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.SEND_SMS),
                1
            )
            _sendSMS(phoneNumber)
        }

    }

    private fun _sendSMS(phoneNumber: String) {
        verificationCode.value = generateVerificationCode()
        val message = "<#> Doğrulama kodunuz: ${verificationCode.value} gqaG5x7ynOQ"
        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)

    }


    fun startCountdown() {
        countdownSecondsRemaining = (countdownDuration / countdownInterval).toInt()
        countdownTimer = object : CountDownTimer(countdownDuration, countdownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                countdownSecondsRemaining--
                countdown.value = countdownSecondsRemaining
                if (countdown.value == 0) {
                    countdownTimer?.cancel()
                }
            }

            override fun onFinish() {
                // reset verification code to null or generate a new code
                verificationCode.value = null
                // verificationCode.value = generateVerificationCode()
                countdown.value = 0
            }
        }.start()
    }

    override fun onCleared() {
        countdownTimer?.cancel()
        super.onCleared()
    }


}

enum class OTPVerificationResult {
    INVALID_OTP,
    OTP_VERIFIED
}