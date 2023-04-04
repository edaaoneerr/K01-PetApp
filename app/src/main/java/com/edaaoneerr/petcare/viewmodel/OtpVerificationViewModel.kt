package com.edaaoneerr.petcare.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.CountDownTimer
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import java.util.regex.Pattern

class OTPVerificationViewModel : ViewModel() {

    private var countdownTimer: CountDownTimer? = null
    private val countdownInterval = 1000L // 1 second
    private val countdownDuration = 120000L // 2 minutes
    private var countdownSecondsRemaining = 0


    val countdown = MutableLiveData<Int>()
    val otpVerificationResult = MutableLiveData<OTPVerificationResult>()


    fun verifyOTP(context: Context, phoneNumber: String, enteredOTP: String): Boolean {
        val projection = arrayOf(Telephony.Sms.BODY)
        val selection = "${Telephony.Sms.ADDRESS} = ?"
        val selectionArgs = arrayOf(phoneNumber)
        val sortOrder = "${Telephony.Sms.DATE} DESC"
        val cursor = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        if (cursor != null && cursor.moveToFirst()) {

            val columnIndex = cursor.getColumnIndex(Telephony.Sms.BODY)
            if (columnIndex >= 0) {
                val messageBody = cursor.getString(columnIndex)
                if (messageBody != null) {
                    val otpPattern = Pattern.compile("(\\d{6})")
                    val otpMatcher = otpPattern.matcher(messageBody)
                    if (otpMatcher.find()) {
                        val receivedOTP = otpMatcher.group(1)
                        otpVerificationResult.value = OTPVerificationResult.OTP_VERIFIED
                        return enteredOTP == receivedOTP
                    }
                } else {

                    Toast.makeText(context, "Wrong otp", Toast.LENGTH_LONG).show()
                }

            }
            cursor.close()
        }
        otpVerificationResult.value = OTPVerificationResult.INVALID_OTP
        return false
    }

    private fun generateVerificationCode(): String {
        val random = Random()
        val code = random.nextInt(999999 - 100000) + 100000
        return code.toString()
    }

    fun sendSMS(context: Context, phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val verificationCode = generateVerificationCode()

            val message = "<#> Doğrulama kodunuz: $verificationCode gqaG5x7ynOQ"
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.SEND_SMS),
                1
            )
        }
    }


    fun startCountdown() {
        countdownSecondsRemaining = (countdownDuration / countdownInterval).toInt()
        countdownTimer = object : CountDownTimer(countdownDuration, countdownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                countdownSecondsRemaining--
                countdown.value = countdownSecondsRemaining
            }

            override fun onFinish() {
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