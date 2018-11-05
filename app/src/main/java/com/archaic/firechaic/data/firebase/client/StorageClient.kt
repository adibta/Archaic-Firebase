package com.archaic.firechaic.data.firebase.client

import com.google.firebase.storage.FirebaseStorage

object StorageClient {

    const val TAG = "StorageClient"

    // For Singleton instantiation
    @Volatile
    private var instance: FirebaseStorage? = null

    private fun getInstance(): FirebaseStorage {
        return instance ?: synchronized(this) {
            instance ?: FirebaseStorage.getInstance()
        }
    }

}