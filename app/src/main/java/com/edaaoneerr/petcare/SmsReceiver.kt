package com.edaaoneerr.petcare

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

class SmsReceiver : BroadcastReceiver() {
    private val TAG = "Sms"
    private val SENDER_PHONE_NUMBER = "+905537020255"

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            for (sms in smsMessages) {
                val senderPhoneNumber = sms.originatingAddress

                if (senderPhoneNumber != null && !senderPhoneNumber.equals(SENDER_PHONE_NUMBER)) {
                    // Process the SMS message
                    Log.d(
                        "SMSVERIFICATION",
                        "SMS received from $senderPhoneNumber: ${sms.messageBody}"
                    )
                }
            }
        }
    }

}






