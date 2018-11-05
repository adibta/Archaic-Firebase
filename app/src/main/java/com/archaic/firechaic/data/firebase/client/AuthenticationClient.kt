package com.archaic.firechaic.data.firebase.client

import com.google.firebase.auth.FirebaseAuth

object AuthenticationClient {

    const val TAG = "FunctionClient"

    // For Singleton instantiation
    @Volatile
    private var instance: FirebaseAuth? = null

    private fun getInstance(): FirebaseAuth {
        return instance ?: synchronized(this) {
            instance ?: FirebaseAuth.getInstance()
        }
    }

}