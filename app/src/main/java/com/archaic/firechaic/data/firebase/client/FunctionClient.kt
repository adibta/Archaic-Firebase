package com.archaic.firechaic.data.firebase.client

import com.google.firebase.functions.FirebaseFunctions

object FunctionClient {

    const val TAG = "FunctionClient"

    // For Singleton instantiation
    @Volatile
    private var instance: FirebaseFunctions? = null

    private fun getInstance(): FirebaseFunctions {
        return instance ?: synchronized(this) {
            instance ?: FirebaseFunctions.getInstance()
        }
    }

}