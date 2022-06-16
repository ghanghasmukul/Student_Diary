package com.example.studentdiary

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FCM : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("aBC", "Refreshed token: $token")

    }

}