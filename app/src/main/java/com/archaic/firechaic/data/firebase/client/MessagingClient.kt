package com.archaic.firechaic.data.firebase.client

import com.google.firebase.messaging.FirebaseMessaging

object MessagingClient {

    const val TAG = "MessagingClient"

    // For Singleton instantiation
    @Volatile
    private var instance: FirebaseMessaging? = null

    private fun getInstance(): FirebaseMessaging {
        return instance ?: synchronized(this) {
            instance ?: FirebaseMessaging.getInstance()
        }
    }

}